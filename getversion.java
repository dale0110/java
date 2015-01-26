/*通过svnkit获取指定目录的svn号码
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

/*
 * This  is  a complex  example program that demonstrates how  you  can manage local
 * working copies as well as  URLs  (that is, items located  in  the  repository) by 
 * means of the  API  provided in the org.tmatesoft.svn.core.wc package. The package 
 * itself represents  a  high-level  API  consisting of classes and interfaces which 
 * allow to perform operations compatible with ones of the native Subversion command
 * line client.  These version control operations are logically grouped in a  set of 
 * classes which names meet  'SVN*Client'  pattern. For example, the package has the 
 * SVNUpdateClient class which is responsible for update-related operations (update,
 * switch, check out). Most of the  methods of these 'client' classes are named like
 * 'doSomething(..)' where 'Something' corresponds to the name of a version  control 
 * operation (usually similar to the name of the  corresponding  Subversion  command 
 * line client's command). So, for  users  familiar with the Subversion command line 
 * client it won't take much  effort and time  to  match a 'do*' method  against  an 
 * appropriate Subversion client's operation (or command, in other words).
 * 
 * Surely, it  may  seem  not  quite handy to deal with a number of classes that all 
 * need to be instantiated, initialized, kept.. For example, if a developer is going 
 * to use all (or several) of the SVN*Client classes and most of them will access  a 
 * repository (in that way when authentication is demanded), it becomes tiresome  to 
 * provide authentication info to every one of them. So, for managing purposes  like 
 * the previous one the  package  has got the class  called  SVNClientManager  whose 
 * get*Client() methods provide all necessary SVN*Client objects to a caller. 
 * 
 * A  developer once creates an instance of  SVNClientManager  providing (if needed) 
 * his  authentication  info/options (options  are  similar  to  the   SVN  run-time 
 * configuration settings that can be found in the config file) into an  appropriate 
 * SVNClientManager.newInstance(..) method. Further all SVN*Client objects  provided 
 * by the instance of SVNClientManager will use these authentication info/options.   
 *  
 * The program illustrates a  number  of  main  operations  usually carried out upon 
 * working copies and URLs. Here's a brief description  of  what  the  program  does 
 * (main steps):
 * 
 * 0)first of all initializes the  SVNKit  library  (it must be done prior to using 
 * the library);
 * 
 * 1)if the program was run with some in parameters, it fetches them out of the args 
 * parameter; the program expects the following input parameters: 
 * 
 * repositoryURL wcRootPath name password 
 * 
 * 2)next instantiates an SVNClientManager providing default options and  auth  info 
 * to it -  these parameters will be used by  all  SVN*Client  objects that will  be
 * created, kept and provided by the manager; default run-time options correspond to 
 * the client-side run-time settings found in the  'config'  file within the default 
 * SVN configuration area; also in this case the client manager will use the server-
 * side run-time settings found in the 'servers' file within the same area;
 * 
 * 3)the first operation  -  making an empty directory in a repository; that is like 
 * 'svn mkdir URL'  which  creates  a  new  directory  given  all  the  intermediate 
 * directories created); this operation is immediately committed to the repository;
 * 
 * 4)the next operation  - creating a new local directory (importDir) and a new file 
 * (importFile) in it and then importing the directory into the repository; 
 * 
 * 5)the next operation - checking out the directory created in the previous step to 
 * a local directory defined by the myWorkingCopyPath parameter ; 
 * 
 * 6)the next operation  -  recursively getting and displaying info for each item at 
 * the working revision in the working copy that was  checked  out  in  the previous 
 * step;
 * 
 * 7)the next operation - creating a new directory (newDir) with a file (newFile) in
 * the working copy and then  recursively  scheduling (if any subdirictories existed 
 * they would be also added:)) the created directory for addition;
 * 
 * 8)the next operation - recursively getting and displaying the working copy status
 * not including unchanged (normal) paths; the result must include those paths which
 * were scheduled for addition in the previous step; 
 * 
 * 9)the next operation  - recursively updating the working copy; if any local items
 * are out of date they will be updated to the latest revision;
 * 
 * 10)the next operation - committing local changes to the repository; this will add 
 * the directory with the file (that were  scheduled  for addition two steps before) 
 * to the repository;
 * 
 * 11)the next operation  -  locking  the  file  committed in the previous step (for 
 * example, if you temporarily need to keep a file locked to prevent someone's  else 
 * modifications);
 * 
 * 12)the next operation  -  showing status once again (here, to see that  the  file 
 * was locked);
 * 
 * 13)the next operation  -  copying  with  history  one  URL (url)  to another  one 
 * (copyURL) within the same repository;
 * 
 * 14)the next operation - switching the working copy to a different  URL  (copyURL)
 * where url was copied to in the previous step;
 * 
 * 15)the next operation  -  recursively  getting  and  displaying  info on the root 
 * directory of the working copy to demonstrate that the working copy is now  really
 * switched against copyURL;
 * 
 * 16)the next operation - scheduling the newDir directory for deletion;
 * 
 * 17)the next operation  -  showing  status  once  again  (here, to  see  that  the 
 * directory with all its entries were scheduled for deletion);
 * 
 * 18)the next operation - committing local changes to the repository; this operation
 * will delete the directory (newDir) with the file (newFile) that were scheduled for
 * deletion from the repository;
 * 
 *                                    * * *
 *                                    
 * This example can be  run  for a locally  installed  Subversion  repository via the 
 * svn:// protocol. This is how you can do it:
 * 
 * 1)after you install the Subversion on your machine (SVN is available for  download 
 * at  http://subversion.tigris.org/),  you should  create  a  new  repository  in  a 
 * directory, like this (in a command line under a Windows OS):
 * 
 * >svnadmin create X:\path\to\rep
 * 
 * 2)after the repository is created you can add a new account: open X:\path\to\rep\, 
 * then move into \conf and open the file - 'passwd'.  In  the file  you'll  see  the 
 * section [users]. Uncomment it and add a new account below the section name, like:
 * 
 * [users] 
 * userName = userPassword
 * 
 * In the program you may further use this account as user's credentials.
 * 
 * 3)the  next  step  is  to  launch  the  custom  Subversion  server (svnserve) in a 
 * background mode for the just created repository:
 * 
 * >svnserve -d -r X:\path\to
 * 
 * That's all. The repository is now available via  svn://localhost/rep.
 * 
 *                                    * * *
 * 
 * While  the  program  is  running, in your console  you'll see something like this:
  
	Making a new directory at 'svn://localhost/testRep/MyRepos'...
	Committed to revision 70
	
	Importing a new directory into 'svn://localhost/testRep/MyRepos/importDir'...
	Adding         importFile.txt
	Committed to revision 71
	
	Checking out a working copy from 'svn://localhost/testRep/MyRepos'...
	A         importDir
	A         importDir/importFile.txt
	At revision 71
	
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy
	URL: svn://localhost/testRep/MyRepos
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 71
	Node Kind: dir
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 71
	Last Changed Date: Thu Jul 21 23:43:15 NOVST 2005
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\importDir
	URL: svn://localhost/testRep/MyRepos/importDir
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 71
	Node Kind: dir
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 71
	Last Changed Date: Thu Jul 21 23:43:15 NOVST 2005
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\importDir\importFile.txt
	URL: svn://localhost/testRep/MyRepos/importDir/importFile.txt
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 71
	Node Kind: file
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 71
	Last Changed Date: Thu Jul 21 23:43:15 NOVST 2005
	Properties Last Updated: Thu Jul 21 23:43:16 NOVST 2005
	Text Last Updated: Thu Jul 21 23:43:16 NOVST 2005
	Checksum: 75e9e68e21ae4453f318424738aef57e
	
	Recursively scheduling a new directory 'N:\MyWorkingCopy\newDir' for addition...
	A     newDir
	A     newDir/newFile.txt
	
	Status for 'N:\MyWorkingCopy':
	A          0     ?    ?                 N:\MyWorkingCopy\newDir\newFile.txt
	A          0     ?    ?                 N:\MyWorkingCopy\newDir
	
	Updating 'N:\MyWorkingCopy'...
	At revision 71
	
	Committing changes for 'N:\MyWorkingCopy'...
	Adding         newDir
	Adding         newDir/newFile.txt
	Transmitting file data....
	Committed to revision 72
	
	Locking (with stealing if the entry is already locked) 'N:\MyWorkingCopy\newDir\newFile.txt'.
	L     newFile.txt
	
	Status for 'N:\MyWorkingCopy':
	     K     72    72    userName         N:\MyWorkingCopy\newDir\newFile.txt
	
	Copying 'svn://localhost/testRep/MyRepos' to 'svn://localhost/testRep/MyReposCopy'...
	Committed to revision 73
	
	Switching 'N:\MyWorkingCopy' to 'svn://localhost/testRep/MyReposCopy'...
	  B       newDir/newFile.txt
	At revision 73
	
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy
	URL: svn://localhost/testRep/MyReposCopy
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 73
	Node Kind: dir
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 73
	Last Changed Date: Thu Jul 21 23:43:19 NOVST 2005
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\importDir
	URL: svn://localhost/testRep/MyReposCopy/importDir
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 73
	Node Kind: dir
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 71
	Last Changed Date: Thu Jul 21 23:43:15 NOVST 2005
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\importDir\importFile.txt
	URL: svn://localhost/testRep/MyReposCopy/importDir/importFile.txt
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 73
	Node Kind: file
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 71
	Last Changed Date: Thu Jul 21 23:43:15 NOVST 2005
	Properties Last Updated: Thu Jul 21 23:43:16 NOVST 2005
	Text Last Updated: Thu Jul 21 23:43:16 NOVST 2005
	Checksum: 75e9e68e21ae4453f318424738aef57e
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\newDir
	URL: svn://localhost/testRep/MyReposCopy/newDir
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 73
	Node Kind: dir
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 72
	Last Changed Date: Thu Jul 21 23:43:18 NOVST 2005
	-----------------INFO-----------------
	Local Path: N:\MyWorkingCopy\newDir\newFile.txt
	URL: svn://localhost/testRep/MyReposCopy/newDir/newFile.txt
	Repository UUID: dbe83c44-f5aa-e043-94ec-ecdf6c56480f
	Revision: 73
	Node Kind: file
	Schedule: normal
	Last Changed Author: userName
	Last Changed Revision: 72
	Last Changed Date: Thu Jul 21 23:43:18 NOVST 2005
	Properties Last Updated: Thu Jul 21 23:43:20 NOVST 2005
	Text Last Updated: Thu Jul 21 23:43:18 NOVST 2005
	Checksum: 023b67e9660b2faabaf84b10ba32c6cf
	
	Scheduling 'N:\MyWorkingCopy\newDir' for deletion ...
	D     newDir/newFile.txt
	D     newDir
	
	Status for 'N:\MyWorkingCopy':
	D          73    72    userName         N:\MyWorkingCopy\newDir\newFile.txt
	D          73    72    userName         N:\MyWorkingCopy\newDir
	
	Committing changes for 'N:\MyWorkingCopy'...
	Deleting   newDir
	Committed to revision 74
 * 
 */
public class WorkingCopy {

    private static SVNClientManager ourClientManager;
    
    public static void main(String[] args) throws SVNException {
        /*
         * Initializes the library (it must be done before ever using the library 
         * itself)
         */
        setupLibrary();


        String name = "zhangchw";
        String password = "BYsvn!@123";
        String myWorkingCopyPath = "E:/SVN/ZXUCP_A200_V1.20_SRC";

        
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
 
        
        System.out.println("\n");
        System.out.println(info.getCommittedDate());
        System.out.println(info.getCommittedRevision());
        System.out.println(info.getAuthor());

        System.out.println();


      

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

    
    
