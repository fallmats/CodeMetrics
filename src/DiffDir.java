/**
 * Created by matseriksson on 19/09/14.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import sun.nio.ch.FileKey;


public class DiffDir {

//    public static boolean areDirsEqual(File dir1, File dir2) {
//        String filter[] = {"java"};
//        return (new HashSet<File>(FileUtils.listFiles(dir1,filter,true )).containsAll(FileUtils.listFiles(dir2,filter,true)));
//    }

    public static SourceFiles  getEqualFiles(File oldFile, File newFile) {
        File fList;
        String filter[] = {"java"};
        //ArrayList<SourceFile> srcList = null;
        SourceFiles dstList = new SourceFiles();

        Collection<File> newFiles = FileUtils.listFiles(newFile, filter, true);

        /*
        TODO: Enhance code to do the check files from both lists.
        TODO: This due to the fact that files can be added and removed in both directories and we need to take care of the case when size has changed in both dirs.
        TODO: As of now we might miss files depending on add/delete
        TODO: But we don't wanna make the function to slow on large source trees
        */


        String path = newFile.getPath();
        for (File f : newFiles) {
            String relativePath = f.getParent().substring(path.length(),f.getParent().length())+"/";
            File toFind = new File( oldFile.getPath() + relativePath + f.getName());
            Boolean b = null;
            try {
                b = FileUtils.directoryContains(oldFile, toFind);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b) {
                Diff diffFile = new Diff();
                //TODO: Do the codeChurn here

                // diffFile.countChurn(oldFiles.getSrcFile(i), newFiles.getSrcFile(i));
                //SourceFile javaSource = new SourceFile(f.getName(), f.getAbsolutePath());
                //dstList.add(javaSource);
            } else {

            }

        }

        return dstList;

    }

    public void diffDirsAndCountCodeChurn(SourceFiles oldFile, SourceFiles newFile) {
        File newFileHandle = new File(newFile.getPath());
        File oldFileHandle = new File(oldFile.getPath());
        String filter[] = {"java"};


        Collection<File> newFiles = FileUtils.listFiles(newFileHandle, filter, true);

        /*
        TODO: Enhance code to do the check files from both lists.
        TODO: As of now we might miss files depending if they are deleted
        TODO: But we don't wanna make the function to slow on large source trees
                */

        String path = newFile.getPath();

        for (int i = 0; i < newFile.getNrOfFiles(); i++) {

            File f = new File(newFile.getSrcFile(i).getFilePath() );

            String relativePath = f.getParent().substring(path.length(),f.getParent().length())+"/";
            File toFind = new File( oldFile.getPath() + relativePath + f.getName());
            Boolean b = null;
            try {
                b = FileUtils.directoryContains(oldFileHandle, toFind);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b) {
                Diff diffFile = new Diff();
                //TODO: Do the codeChurn here
                SourceFile currentOldFile = new SourceFile(toFind.getName(), toFind.getAbsolutePath());;
                diffFile.countChurn(currentOldFile, newFile.getSrcFile(i));

            } else {
                newFile.getSrcFile(i).setNew(true);
            }

        }

    }



}
