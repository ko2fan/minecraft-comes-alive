package mca.core.radix;

import java.io.File;
import java.util.Scanner;

import mca.core.MCA;
import radixcore.ModCrashWatcher;

public class CrashWatcher extends ModCrashWatcher
{
	@Override
	protected void onCrash(File crashFile) 
	{
		try
		{
			Scanner scanner = new Scanner(crashFile);
			String fileContent = scanner.useDelimiter("\\Z").next();

			MCA.getLog().fatal("Crash detected!");
			//RDXServerBridge.sendCrashReport(fileContent, true);
			
			scanner.close();
		}

		catch (Exception e)
		{
			//TODO
		}
	}
}
