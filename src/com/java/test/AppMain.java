package com.java.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppMain extends Thread
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
//		String projPath = System.getProperty("user.dir");
//		System.out.println("path : " + projPath);

		String dir = "C:\\watchServiceTest";
		WatchService service = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(dir);
		path.register(service, 
						StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_MODIFY,
						StandardWatchEventKinds.ENTRY_DELETE);
		
		while(true)
		{
			WatchKey key = service.take();	
//			WatchKey key = service.poll(10, TimeUnit.SECONDS);
			
			List<WatchEvent<?>> list = key.pollEvents(); 			// Event List  
			
			for(WatchEvent<?> event : list)
			{
				Kind<?> kind = event.kind();
				Path pth = (Path)event.context();
				
				if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE))
				{
					System.out.println("생성 : " + pth.getFileName());
				}
				else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY))
				{
					update(pth);
				}
				else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE))
				{
					System.out.println("삭제: " + pth.getFileName());
				}
				else if(kind.equals(StandardWatchEventKinds.OVERFLOW))
				{
					System.out.println("오버플로우");					// 등록 디렉토리 삭제 시
 				}
			}
			
			if(!key.reset()) break;
		}
		
		service.close();
	}

	public static void update(Path pth)
	{
		System.out.println("�닔�젙�씪�뼱�궗�떎");
		System.out.println("getFileName" + pth.getFileName());
		System.out.println("getFileSystem" + pth.getFileSystem());
		System.out.println("getNameCount" + pth.getNameCount());
		System.out.println("getName MAX : " + pth.getName(0));
//		System.out.println("getName MIN : " + pth.getName(MIN_PRIORITY));
//		System.out.println("getName NORM : " + pth.getName(NORM_PRIORITY));
		System.out.println("getParent" + pth.getParent());
		System.out.println("getRoot" + pth.getRoot());
	}

}
