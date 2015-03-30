/**
 * Created by matseriksson on 19/09/14.
 */

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import se.prolore.fileparsing.JavaSourceParser;
import se.prolore.fileparsing.SourceFiles;
import se.prolore.fileparsing.SourceFile;


public class DiffDir {

    public int maxCodeChurn = 0;
    public int maxELoc = 0;

//    public static boolean areDirsEqual(File dir1, File dir2) {
//        String filter[] = {"java"};
//        return (new HashSet<File>(FileUtils.listFiles(dir1,filter,true )).containsAll(FileUtils.listFiles(dir2,filter,true)));
//    }

//    public static se.prolore.fileparsing.SourceFiles  getEqualFiles(File oldFile, File newFile) {
//        File fList;
//        String filter[] = {"java"};
//        //ArrayList<SourceFile> srcList = null;
//        se.prolore.fileparsing.SourceFiles dstList = new se.prolore.fileparsing.SourceFiles();
//
//        Collection<File> newFiles = FileUtils.listFiles(newFile, filter, true);
//
//        /*
//        TODO: Enhance code to do the check files from both lists.
//        TODO: This due to the fact that files can be added and removed in both directories and we need to take care of the case when size has changed in both dirs.
//        TODO: As of now we might miss files depending on add/delete
//        TODO: But we don't wanna make the function to slow on large source trees
//        */
//
//
//        String path = newFile.getPath();
//        for (File f : newFiles) {
//            String relativePath = f.getParent().substring(path.length(),f.getParent().length())+"/";
//            File toFind = new File( oldFile.getPath() + relativePath + f.getName());
//            Boolean b = null;
//            try {
//                b = FileUtils.directoryContains(oldFile, toFind);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (b) {
//                Diff diffFile = new Diff();
//                //TODO: Do the codeChurn here
//
//                // diffFile.countChurn(oldFiles.getSrcFile(i), newFiles.getSrcFile(i));
//                //SourceFile javaSource = new SourceFile(f.getName(), f.getAbsolutePath());
//                //dstList.add(javaSource);
//            } else {
//
//            }
//
//        }
//
//        return dstList;
//
//    }

    public void diffDirsAndCountCodeChurn(SourceFiles oldFile, SourceFiles newFile) {
        File newFileHandle = new File(newFile.getPath());
        File oldFileHandle = new File(oldFile.getPath());
        String filter[] = {"java"};


        //Collection<File> newFiles = FileUtils.listFiles(newFileHandle, filter, true);

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
                SourceFile currentOldFile = new JavaSourceParser(toFind.getName(), toFind.getAbsolutePath());;
                try {
                    diffFile.countChurn(currentOldFile, newFile.getSrcFile(i));
                    if (newFile.getSrcFile(i).getCodeChurn() > maxCodeChurn ) {
                        maxCodeChurn = newFile.getSrcFile(i).getCodeChurn();
                    }
                    if (newFile.getSrcFile(i).getLinesOfStatements() > maxELoc) {
                        maxELoc = newFile.getSrcFile(i).getLinesOfStatements();
                    }
                } catch (Exception e) {
                    System.out.println("Found error when calculating Code Churn for file: " + newFile.getSrcFile(i).getFileName());
                    //System.out.println(e);
                    e.printStackTrace();

                }

            } else {
                newFile.getSrcFile(i).setNew(true);
            }

        }

    }



}
