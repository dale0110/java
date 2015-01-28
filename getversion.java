/*通过svnkit获取指定目录的svn号码
/*
 * ====================================================================
 * Copyright (c) 2004-2008 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://svnkit.com/license.html
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */
package org.tmatesoft.svn.examples.wc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class WorkingCopy {

    private static SVNClientManager ourClientManager;
    
    public  static void writeFileByFileWriter(String filePath, String content) throws IOException{
		File file = new File(filePath);
		synchronized (file) {
			FileWriter fw = new FileWriter(filePath);
			fw.write(content);
			fw.close();
		}
	}
    
    public static void main(String[] args) throws SVNException {
        /*
         * Initializes the library (it must be done before ever using the library 
         * itself)
         */
    	
    	/*
    	if(args.length < 5)
    	{
            System.out.println("please input:getSVNVersion user password workingdir");
            System.exit(1);
    	}
    	*/
        setupLibrary();

        
        //String name = args[1];
        //String password = args[2];
        String name = "zhangchw";
        String password = "BYsvn!@123";
        //String myWorkingCopyPath = args[3];
        String myWorkingCopyPath ="E://SVN//ZXUCP_A200_V1.20_SRC";
        //String macro_file = args[4];
        String macro_file = "E://SVN//ZXUCP_A200_V1.20_SRC//test.h";
        
        /*
         * Creates a default run-time configuration options driver. Default options 
         * created in this way use the Subversion run-time configuration area (for 
         * instance, on a Windows platform it can be found in the '%APPDATA%\Subversion' 
         * directory). 
         * 
         * readonly = true - not to save  any configuration changes that can be done 
         * during the program run to a config file (config settings will only 
         * be read to initialize; to enable changes the readonly flag should be set
         * to false).
         * 
         * SVNWCUtil is a utility class that creates a default options driver.
         */
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        
        /*
         * Creates an instance of SVNClientManager providing authentication
         * information (name, password) and an options driver
         */
        ourClientManager = SVNClientManager.newInstance(options, name, password);
        
        /*
         * creates a local directory where the working copy will be checked out into
         */
        File wcDir = new File(myWorkingCopyPath);
       
        
        SVNInfo info = ourClientManager.getWCClient().doInfo(wcDir, SVNRevision.WORKING);
 
        

        System.out.println(info.getCommittedDate());
        System.out.println(info.getCommittedRevision());
        System.out.println(info.getAuthor());
        
        String macro_content="#define  VERSION_TIME        "+
        		"\""+
        		info.getCommittedDate()+
        		"\""+
        		"\n"+
        		"#define  SVN_VERSION        "+
        		info.getCommittedRevision();
        
        
        try {
			writeFileByFileWriter(macro_file,macro_content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.exit(0);
    }

    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }
  
    
    /*
     * Displays error information and exits. 
     */
    private static void error(String message, Exception e){
        System.err.println(message+(e!=null ? ": "+e.getMessage() : ""));
        System.exit(1);
    }
}
    
    /*
     * This method does not relate to SVNKit API. Just a method which creates
     * local directories and files :)
     */

    
    
    
    
