/***********************************************************************\
*
* $Revision: 988 $
* $Date: 2012-07-17 13:49:11 +0200 (Tue, 17 Jul 2012) $
* $Author: torsten $
* Contents: dynamic list functions
* Systems: all
*
\***********************************************************************/

#define __LISTS_IMPLEMENATION__

/****************************** Includes *******************************/

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#ifndef NDEBUG
  #include <pthread.h>
#endif /* not NDEBUG */
#ifdef HAVE_BACKTRACE
  #include <execinfo.h>
#endif
#include <assert.h>

#include "linkedlist.h"

/****************** Conditional compilation switches *******************/

/***************************** Constants *******************************/
#define DEBUG_MAX_FREE_LIST 4000

/***************************** Datatypes *******************************/
#ifndef NDEBUG
  // list of nodes
  typedef struct DebugListNode
  {
    LIST_NODE_HEADER(struct DebugListNode);

    const char      *fileName;
    ulong           lineNb;
    #ifdef HAVE_BACKTRACE
      void const *stackTrace[16];
      int        stackTraceSize;
    #endif /* HAVE_BACKTRACE */

    const char      *deleteFileName;
    ulong           deleteLineNb;
    #ifdef HAVE_BACKTRACE
      void const *deleteStackTrace[16];
      int        deleteStackTraceSize;
    #endif /* HAVE_BACKTRACE */

    const Node *node;
  } DebugListNode;

  typedef struct
  {
    LIST_HEADER(DebugListNode);
  } DebugListNodeList;
#endif /* not NDEBUG */

/***************************** Variables *******************************/
#ifndef NDEBUG
  static pthread_once_t      debugListInitFlag = PTHREAD_ONCE_INIT;
  static pthread_mutexattr_t debugListLockAttributes;
  static pthread_mutex_t     debugListLock;
  static DebugListNodeList   debugListAllocNodeList;
  static DebugListNodeList   debugListFreeNodeList;
#endif /* not NDEBUG */

/****************************** Macros *********************************/

/***************************** Forwards ********************************/

/***************************** Functions *******************************/

#ifdef __cplusplus
  extern "C" {
#endif

/***********************************************************************\
* Name   : listInsert
* Purpose: insert node into list
* Input  : list     - list
*          node     - node to insert
*          nextNode - next node in list or NULL
* Output : -
* Return : -
* Notes  : -
\***********************************************************************/

inline void listInsert(void *list,
                             void *node,
                             void *nextNode
                            )
{
  assert(list != NULL);
  assert(node != NULL);

  assert(((((List*)list)->count == 0) && (((List*)list)->head == NULL) && (((List*)list)->tail == NULL)) ||
         ((((List*)list)->count > 0) && (((List*)list)->head != NULL) && (((List*)list)->tail != NULL))
        );
  assert(node != NULL);

  if      (nextNode != NULL)
  {
    // insert in middle of list
    ((Node*)node)->prev = ((Node*)nextNode)->prev;
    ((Node*)node)->next = ((Node*)nextNode);
    if (((Node*)nextNode)->prev != NULL) ((Node*)nextNode)->prev->next = node;
    ((Node*)nextNode)->prev = node;

    if (((List*)list)->head == nextNode) ((List*)list)->head = node;
    ((List*)list)->count++;
  }
  else if (((List*)list)->head != NULL)
  {
    // append to end of list
    ((Node*)node)->prev = ((List*)list)->tail;
    ((Node*)node)->next = NULL;

    ((List*)list)->tail->next = node;
    ((List*)list)->tail = node;
    ((List*)list)->count++;
  }
  else
  {
    // insert as first node
    ((Node*)node)->prev = NULL;
    ((Node*)node)->next = NULL;

    ((List*)list)->head  = node;
    ((List*)list)->tail  = node;
    ((List*)list)->count = 1;
  }

  assert(((((List*)list)->count == 0) && (((List*)list)->head == NULL) && (((List*)list)->tail == NULL)) ||
         ((((List*)list)->count > 0) && (((List*)list)->head != NULL) && (((List*)list)->tail != NULL))
        );
}

/***********************************************************************\
* Name   : listAppend
* Purpose: append node to list
* Input  : list - list
*          node - node to insert
* Output : -
* Return : -
* Notes  : -
\***********************************************************************/

inline void listAppend(void *list,
                             void *node
                            )
{
  listInsert(list,node,NULL);
}
/***********************************************************************\
* Name   : listRemove
* Purpose: remove node from list
* Input  : list - list
*          node - node to remove
* Output : -
* Return : -
* Notes  : -
\***********************************************************************/

inline void listRemove(void *list,
                             void *node
                            )
{
  assert(list != NULL);
  assert(((List*)list)->head != NULL);
  assert(((List*)list)->tail != NULL);
  assert(((List*)list)->count > 0);
  assert(node != NULL);

  if (((Node*)node)->prev != NULL) ((Node*)node)->prev->next = ((Node*)node)->next;
  if (((Node*)node)->next != NULL) ((Node*)node)->next->prev = ((Node*)node)->prev;
  if ((Node*)node == ((List*)list)->head) ((List*)list)->head = ((Node*)node)->next;
  if ((Node*)node == ((List*)list)->tail) ((List*)list)->tail = ((Node*)node)->prev;
  ((List*)list)->count--;

  assert(((((List*)list)->count == 0) && (((List*)list)->head == NULL) && (((List*)list)->tail == NULL)) ||
         ((((List*)list)->count > 0) && (((List*)list)->head != NULL) && (((List*)list)->tail != NULL))
        );
}

/***********************************************************************\
* Name   : listContains
* Purpose: check if node is in list
* Input  : list - list
*          node - node to search for
* Output : -
* Return : TRUE if node is in list, FALSE otherwise
* Notes  : -
\***********************************************************************/

inline bool listContains(void *list,
                               void *node
                              )
{
  Node *listNode;

  assert(list != NULL);
  assert(node != NULL);

  listNode = ((List*)list)->head;
  while (listNode != node)
  {
    listNode = listNode->next;
  }

  return listNode != NULL;
}

/***********************************************************************\
* Name   : debugListInit
* Purpose: initialize debug functions
* Input  : -
* Output : -
* Return : -
* Notes  : -
\***********************************************************************/

#ifndef NDEBUG
static void debugListInit(void)
{
  pthread_mutexattr_init(&debugListLockAttributes);
  pthread_mutexattr_settype(&debugListLockAttributes,PTHREAD_MUTEX_RECURSIVE);
  pthread_mutex_init(&debugListLock,&debugListLockAttributes);
  List_init(&debugListAllocNodeList);
  List_init(&debugListFreeNodeList);
}
#endif /* not NDEBUG */

// ----------------------------------------------------------------------

#ifndef NDEBUG
/***********************************************************************\
* Name   : checkDuplicateNode
* Purpose: check if node is already in list
* Input  : fileName - code file name
*          lineNb   - code line number
*          list     - list where node should be inserted
*          newNode  - new node to insert
* Output : -
* Return : -
* Notes  : -
\***********************************************************************/

static void checkDuplicateNode(const char *fileName,
                              ulong      lineNb,
                              const List *list,
                              const Node *newNode
                             )
{
  Node *node;

  assert(list != NULL);

  node = list->head;
  while (node != NULL)
  {
    if (node == newNode)
    {
      HALT_INTERNAL_ERROR_AT(fileName,lineNb,"Node %p is already in list %p!",node,list);
    }
    node = node->next;
  }
}
#endif /* not NDEBUG */

#ifdef NDEBUG
Node * List_newNode(ulong size)
#else /* NDEBUG */
Node * __List_newNode(const char *__fileName__, ulong __lineNb__, ulong size)
#endif /*NDEBUG */
{
  Node *node;
  #ifndef NDEBUG
    DebugListNode *debugListNode;
  #endif /* not NDEBUG */

  // allocate node
  node = (Node*)malloc(size);
  if (node == NULL)
  {
    return NULL;
  }

  // add to allocated node list
  #ifndef NDEBUG
    pthread_once(&debugListInitFlag,debugListInit);

    pthread_mutex_lock(&debugListLock);
    {
      // find node in free-list; reuse or allocate new debug node
      debugListNode = debugListFreeNodeList.head;
      while ((debugListNode != NULL) && (debugListNode->node != node))
      {
        debugListNode = debugListNode->next;
      }
      if (debugListNode != NULL)
      {
        listRemove(&debugListFreeNodeList,debugListNode);
      }
      else
      {
        debugListNode = (DebugListNode*)malloc(sizeof(DebugListNode));
        if (debugListNode == NULL)
        {
          HALT_INSUFFICIENT_MEMORY();
        }
      }

      // init list node
      debugListNode->fileName = __fileName__;
      debugListNode->lineNb   = __lineNb__;
      debugListNode->node     = node;
      #ifdef HAVE_BACKTRACE
        debugListNode->stackTraceSize       = backtrace((void*)debugListNode->stackTrace,SIZE_OF_ARRAY(debugListNode->stackTrace));
        debugListNode->deleteStackTraceSize = 0;
      #endif /* HAVE_BACKTRACE */
      listAppend(&debugListAllocNodeList,debugListNode);
    }
    pthread_mutex_unlock(&debugListLock);
  #endif /*NDEBUG */

  return node;
}

#ifdef NDEBUG
Node *List_deleteNode(Node *node)
#else /* NDEBUG */
Node *__List_deleteNode(const char *__fileName__, ulong __lineNb__, Node *node)
#endif /*NDEBUG */
{
  Node *nextNode;
  #ifndef NDEBUG
    DebugListNode *debugListNode;
  #endif /* not NDEBUG */

  assert(node != NULL);

  // remove from allocated node list, add to node free list, shorten list
  #ifndef NDEBUG
    pthread_once(&debugListInitFlag,debugListInit);

    pthread_mutex_lock(&debugListLock);
    {
      // find node in free-list to check for duplicate free
      debugListNode = debugListFreeNodeList.head;
      while ((debugListNode != NULL) && (debugListNode->node != node))
      {
        debugListNode = debugListNode->next;
      }
      if (debugListNode != NULL)
      {
        fprintf(stderr,"DEBUG WARNING: multiple free of node %p at %s, %lu and previously at %s, %lu which was allocated at %s, %ld!\n",
                node,
                __fileName__,
                __lineNb__,
                debugListNode->deleteFileName,
                debugListNode->deleteLineNb,
                debugListNode->fileName,
                debugListNode->lineNb
               );
        #ifdef HAVE_BACKTRACE
          debugDumpStackTrace(stderr,"allocated at",2,debugListNode->stackTrace,debugListNode->stackTraceSize);
          debugDumpStackTrace(stderr,"deleted at",2,debugListNode->deleteStackTrace,debugListNode->deleteStackTraceSize);
        #endif /* HAVE_BACKTRACE */
        HALT_INTERNAL_ERROR("");
      }

      // remove node from allocated list, add node to free-list, shorten list
      debugListNode = debugListAllocNodeList.head;
      while ((debugListNode != NULL) && (debugListNode->node != node))
      {
        debugListNode = debugListNode->next;
      }
      if (debugListNode != NULL)
      {
        // remove from allocated list
        listRemove(&debugListAllocNodeList,debugListNode);

        // add to free list
        debugListNode->deleteFileName = __fileName__;
        debugListNode->deleteLineNb   = __lineNb__;
        #ifdef HAVE_BACKTRACE
          debugListNode->deleteStackTraceSize = backtrace((void*)debugListNode->deleteStackTrace,SIZE_OF_ARRAY(debugListNode->deleteStackTrace));
        #endif /* HAVE_BACKTRACE */
        listAppend(&debugListFreeNodeList,debugListNode);

        // shorten free list
        while (debugListFreeNodeList.count > DEBUG_MAX_FREE_LIST)
        {
          debugListNode = (DebugListNode*)List_getFirst(&debugListFreeNodeList);
          free(debugListNode);
        }
      }
      else
      {
        fprintf(stderr,"DEBUG WARNING: node %p not found in debug list at %s, line %lu\n",
                node,
                __fileName__,
                __lineNb__
               );
        #ifdef HAVE_BACKTRACE
          debugDumpCurrentStackTrace(stderr,"",0);
        #endif /* HAVE_BACKTRACE */
        HALT_INTERNAL_ERROR("");
      }
    }
    pthread_mutex_unlock(&debugListLock);
  #endif /*NDEBUG */

  // get next node, free node
  nextNode = node->next;
  free(node);

  return nextNode;
}

void List_init(void *list)
{
  assert(list != NULL);

  ((List*)list)->head  = NULL;
  ((List*)list)->tail  = NULL;
  ((List*)list)->count = 0;
}

void List_done(void                 *list,
               ListNodeFreeFunction listNodeFreeFunction,
               void                 *listNodeFreeUserData
              )
{
  assert(list != NULL);

  List_clear(list,listNodeFreeFunction,listNodeFreeUserData);
}

List *List_new(void)
{
  List *list;

  list = (List*)malloc(sizeof(List));
  if (list == NULL) return NULL;

  List_init(list);

  return list;
}

List *List_duplicate(const void           *fromList,
                     const void           *fromListFromNode,
                     const void           *fromListToNode,
                     ListNodeCopyFunction listNodeCopyFunction,
                     void                 *listNodeCopyUserData
                    )
{
  List *list;

  assert(fromList != NULL);
  assert(listNodeCopyFunction != NULL);

  list = (List*)malloc(sizeof(List));
  if (list == NULL) return NULL;

  List_init(list);
  List_copy(fromList,
            list,
            fromListFromNode,
            fromListToNode,
            NULL,
            listNodeCopyFunction,
            listNodeCopyUserData
           );

  return list;
}

void List_delete(void                 *list,
                 ListNodeFreeFunction listNodeFreeFunction,
                 void                 *listNodeFreeUserData
                )
{
  assert(list != NULL);

  List_done(list,listNodeFreeFunction,listNodeFreeUserData);\
  free(list);
}

void List_clear(void                 *list,
                ListNodeFreeFunction listNodeFreeFunction,
                void                 *listNodeFreeUserData
               )
{
  Node *node;

  assert(list != NULL);

  if (listNodeFreeFunction != NULL)
  {
    while (((List*)list)->tail != NULL)
    {
      node = ((List*)list)->tail;
      ((List*)list)->tail = ((List*)list)->tail->prev;
      listNodeFreeFunction(node,listNodeFreeUserData);
      LIST_DELETE_NODE(node);
    }
  }
  else
  {
    while (((List*)list)->tail != NULL)
    {
      node = ((List*)list)->tail;
      ((List*)list)->tail = ((List*)list)->tail->prev;
      LIST_DELETE_NODE(node);
    }
  }
  ((List*)list)->head  = NULL;
  ((List*)list)->count = 0;
}

void List_copy(const void           *fromList,
               void                 *toList,
               const void           *fromListFromNode,
               const void           *fromListToNode,
               void                 *toListNextNode,
               ListNodeCopyFunction listNodeCopyFunction,
               void                 *listNodeCopyUserData
              )
{
  Node *node;
  Node *newNode;

  assert(fromList != NULL);
  assert(toList != NULL);
  assert(listNodeCopyFunction != NULL);

  if (fromListFromNode == LIST_START) fromListFromNode = ((List*)fromList)->head;

  node = (Node*)fromListFromNode;
  while (node != fromListToNode)
  {
    newNode = listNodeCopyFunction(node,listNodeCopyUserData);
    List_insert(toList,newNode,toListNextNode);
    node = node->next;
  }
  if (node != NULL)
  {
    newNode = listNodeCopyFunction(node,listNodeCopyUserData);
    List_insert(toList,newNode,toListNextNode);
  }
}

void List_move(void *fromList,
               void *toList,
               void *fromListFromNode,
               void *fromListToNode,
               void *toListNextNode
              )
{
  Node *node;
  Node *nextNode;

  assert(fromList != NULL);
  assert(toList != NULL);

  if (fromListFromNode == LIST_START) fromListFromNode = ((List*)fromList)->head;

  node = (Node*)fromListFromNode;
  while (node != fromListToNode)
  {
    nextNode = node->next;
    listRemove(fromList,node);
    listInsert(toList,node,toListNextNode);
    node = nextNode;
  }
  if (node != NULL)
  {
    listRemove(fromList,node);
    listInsert(toList,node,toListNextNode);
  }
}

#ifdef NDEBUG
void List_insert(void *list,
                 void *node,
                 void *nextNode
                )
#else /* NDEBUG */
void __List_insert(const char *fileName,
                   ulong      lineNb,
                   void       *list,
                   void       *node,
                   void       *nextNode
                  )
#endif /*NDEBUG */
{
  assert(list != NULL);
  assert(node != NULL);

  #ifndef NDEBUG
    checkDuplicateNode(fileName,lineNb,(List*)list,(Node*)node);
  #endif /* not NDEBUG */

  listInsert(list,node,nextNode);
}

#ifdef NDEBUG
void List_append(void *list,
                 void *node
                )
#else /* NDEBUG */
void __List_append(const char *fileName,
                   ulong      lineNb,
                   void       *list,
                   void       *node
                  )
#endif /* NDEBUG */
{
  assert(list != NULL);
  assert(node != NULL);

  #ifdef NDEBUG
    List_insert(list,node,NULL);
  #else /* NDEBUG */
    __List_insert(fileName,lineNb,list,node,NULL);
  #endif /* NDEBUG */
}

void *List_remove(void *list,
                  void *node
                 )
{
  void *nextNode;

  assert(list != NULL);
  assert(node != NULL);

  nextNode = ((Node*)node)->next;
  listRemove(list,node);

  return nextNode;
}

Node *List_getFirst(void *list)
{
  Node *node;

  assert(list != NULL);

  node = List_first(list);
  if (node != NULL) listRemove(list,node);

  return node;
}

Node *List_getLast(void *list)
{
  Node *node;

  assert(list != NULL);

  node = List_last(list);
  if (node != NULL) listRemove(list,node);

  return node;
}

const Node *List_findFirst(const void             *list,
                           ListNodeEqualsFunction listNodeEqualsFunction,
                           void                   *listNodeEqualsUserData
                          )
{
  Node *node;

  assert(list != NULL);
  assert(listNodeEqualsFunction != NULL);

  node = ((List*)list)->head;
  while ((node != NULL) && (listNodeEqualsFunction(node,listNodeEqualsUserData) != 0))
  {
    node = node->next;
  }

  return node;
}

const Node *List_findNext(const void             *list,
                          const void             *node,
                          ListNodeEqualsFunction listNodeEqualsFunction,
                          void                   *listNodeEqualsUserData
                         )
{
  assert(list != NULL);
  assert(listNodeEqualsFunction != NULL);

  UNUSED_VARIABLE(list);

  if (node != NULL)
  {
    node = (((Node*)node))->next;
    while ((node != NULL) && (listNodeEqualsFunction(node,listNodeEqualsUserData) != 0))
    {
      node = (((Node*)node))->next;
    }
  }

  return node;
}

#if 0
void pp(void *list)
{
  void *node;

printf("---\n");
  node = ((List*)list)->head;
  while (node != NULL)
  {
printf("%p\n",node);
node = ((Node*)node)->next;
  }
}
#endif /* 0 */

void List_sort(void                    *list,
               ListNodeCompareFunction listNodeCompareFunction,
               void                    *listNodeCompareUserData
              )
{
  List  sortedList;
  void  *node1,*node2;
  ulong n;
  bool  mergedFlag;
  ulong i;
  ulong n1,n2;
  void  *node;

  assert(list != NULL);
  assert(listNodeCompareFunction != NULL);

//pp(list);

  /* sort list with merge-sort */
  n = 1;
  do
  {
    sortedList.head = NULL;
    sortedList.tail = NULL;

    mergedFlag = false;
    node1 = ((List*)list)->head;
    while (node1 != NULL)
    {
      /* find start of sub-list 2 */
      node2 = node1;
      for (i = 0; (i < n) && (node2 != NULL); i++)
      {
        node2 = ((Node*)node2)->next;
      }

      /* merge */
      n1 = n;
      n2 = n;
      while (((n1 > 0) && (node1 != NULL)) || ((n2 > 0) && (node2 != NULL)))
      {
        /* select next node to add to sorted list */
        if      ((n1 == 0) || (node1 == NULL))
        {
          /* sub-list 1 is empty -> select node from sub-list 2 */
          node = node2; node2 = ((Node*)node2)->next; n2--;
        }
        else if ((n2 == 0) || (node2 == NULL))
        {
          /* sub-list 2 is empty -> select node from sub-list 1 */
          node = node1; node1 = ((Node*)node1)->next; n1--;
        }
        else
        {
          /* compare nodess from sub-list 1 and 2 */
          if (listNodeCompareFunction(node1,node2,listNodeCompareUserData) < 0)
          {
            /* node1 < node2 -> select node1 */
            node = node1; node1 = ((Node*)node1)->next; n1--;
          }
          else
          {
            /* node1 >= node2 -> select node2 */
            node = node2; node2 = ((Node*)node2)->next; n2--;
          }
          mergedFlag = true;
        }

        /* add to list */
        ((Node*)node)->prev = sortedList.tail;
        ((Node*)node)->next = NULL;
        if (sortedList.head != NULL)
        {
          sortedList.tail->next = node;
          sortedList.tail = node;
        }
        else
        {
          sortedList.head = node;
          sortedList.tail = node;
        }
      }
//pp(&sortedList);

      /* next sub-lists */
      node1 = node2;
    }

    /* next sub-list size */
    ((List*)list)->head = sortedList.head;
    ((List*)list)->tail = sortedList.tail;
    n *= 2;
  }
  while (mergedFlag);
}

#ifndef NDEBUG
void List_debugDone(void)
{
  pthread_once(&debugListInitFlag,debugListInit);

  List_debugCheck();

  pthread_mutex_lock(&debugListLock);
  {
    while (!List_isEmpty(&debugListFreeNodeList))
    {
      free(List_getFirst(&debugListFreeNodeList));
    }
    while (!List_isEmpty(&debugListFreeNodeList))
    {
      free(List_getFirst(&debugListFreeNodeList));
    }
  }
  pthread_mutex_unlock(&debugListLock);
}

void List_debugDumpInfo(FILE *handle)
{
  DebugListNode *debugListNode;

  pthread_once(&debugListInitFlag,debugListInit);

  pthread_mutex_lock(&debugListLock);
  {
    LIST_ITERATE(&debugListAllocNodeList,debugListNode)
    {
      fprintf(handle,"DEBUG: list node %p allocated at %s, line %lu\n",
              debugListNode->node,
              debugListNode->fileName,
              debugListNode->lineNb
             );
      #ifdef HAVE_BACKTRACE
        debugDumpStackTrace(handle,"allocated at",2,debugListNode->stackTrace,debugListNode->stackTraceSize);
      #endif /* HAVE_BACKTRACE */
    }
  }
  pthread_mutex_unlock(&debugListLock);
}

void List_debugPrintInfo()
{
  List_debugDumpInfo(stderr);
}

void List_debugPrintStatistics(void)
{
  pthread_once(&debugListInitFlag,debugListInit);

  pthread_mutex_lock(&debugListLock);
  {
    fprintf(stderr,"DEBUG: %lu list node(s) allocated\n",
            List_count(&debugListAllocNodeList)
           );
    fprintf(stderr,"DEBUG: %lu list node(s) in deleted list\n",
            List_count(&debugListFreeNodeList)
           );
  }
  pthread_mutex_unlock(&debugListLock);
}

void List_debugCheck()
{
  pthread_once(&debugListInitFlag,debugListInit);

  List_debugPrintInfo();
  List_debugPrintStatistics();

  pthread_mutex_lock(&debugListLock);
  {
    if (!List_isEmpty(&debugListAllocNodeList))
    {
      HALT_INTERNAL_ERROR_LOST_RESOURCE();
    }
  }
  pthread_mutex_unlock(&debugListLock);
}
#endif /* not NDEBUG */

#ifdef __cplusplus
  }
#endif

/* end of file */
