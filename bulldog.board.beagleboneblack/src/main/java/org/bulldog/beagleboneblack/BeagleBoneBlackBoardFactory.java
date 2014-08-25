package org.bulldog.beagleboneblack;

import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.BoardFactory;
import org.bulldog.linux.sysinfo.KernelInfo;
import org.bulldog.linux.util.LinuxLibraryLoader;

public class BeagleBoneBlackBoardFactory implements BoardFactory {

	@Override
	public boolean isCompatibleWithPlatform() {
		boolean compatible = true;
		if(KernelInfo.getArchitectureVersion() == 3 &&
				   KernelInfo.getMajorRelease() > 8) {
			System.out.println("Beaglebone Black: Kernel version not compatible: Only 3.8.* Kernels are supported");
			compatible = false;
		}
		
		if(KernelInfo.getArchitectureVersion() < 3) {
			System.out.println("Beaglebone Black: Kernel version not compatible: Only 3.8.* Kernels are supported");
			compatible = false;
		}
		
		System.out.println("Beaglebone Black: Found kernel version " + KernelInfo.getRelease());
		return compatible;
	}

	@Override
	public Board createBoard() {
		LinuxLibraryLoader.loadNativeLibrary();
		return new BeagleBoneBlack();
	}

}
