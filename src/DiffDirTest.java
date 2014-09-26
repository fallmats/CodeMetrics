import org.junit.Test;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;

public class DiffDirTest {


    @org.junit.Test
    public void testDiffDirsAndCountCodeChurnEqualDirs() throws Exception {

        SourceFiles oldFiles = new SourceFiles();
        SourceFiles newFiles = new SourceFiles();

        File f1 = new File("resources/diffTree/structure1");
        File f2 = new File("resources/diffTree/structure2");
        newFiles.parseSrcDir(f1);
        newFiles.setPath(f1.getAbsolutePath());
        oldFiles.parseSrcDir(f2);
        oldFiles.setPath(f2.getAbsolutePath());

        DiffDir d = new DiffDir();
        d.diffDirsAndCountCodeChurn(oldFiles, newFiles);
        newFiles.countLines();
        newFiles.countComplexity();

        Reporting r = new Reporting();
        r.printReport(newFiles, true);
        assertNotNull("My list was empty",newFiles);
        //System.out.println(myList.size());

    }

    @org.junit.Test
    public void testDiffDirsAndCountCodeChurnDifferentDirs() throws Exception {

        SourceFiles oldFiles = new SourceFiles();
        SourceFiles newFiles = new SourceFiles();

        File f1 = new File("resources/diffTree/structure1");
        File f2 = new File("resources/diffTree/structure3");
        newFiles.parseSrcDir(f1);
        newFiles.setPath(f1.getAbsolutePath());
        oldFiles.parseSrcDir(f2);
        oldFiles.setPath(f2.getAbsolutePath());

        DiffDir d = new DiffDir();
        d.diffDirsAndCountCodeChurn(oldFiles, newFiles);
        newFiles.countLines();
        newFiles.countComplexity();

        Reporting r = new Reporting();
        r.printReport(newFiles, true);
        assertNotNull("My list was empty",newFiles);
        //System.out.println(myList.size());

    }

    @org.junit.Test
    public void testDiffDirsAndCountCodeChurnDifferentDirsShowAddedComplexity() throws Exception {

        SourceFiles oldFiles = new SourceFiles();
        SourceFiles newFiles = new SourceFiles();

        File f1 = new File("resources/diffTree/structure1");
        File f2 = new File("resources/diffTree/structure3");
        newFiles.parseSrcDir(f1);
        newFiles.setPath(f1.getAbsolutePath());
        oldFiles.parseSrcDir(f2);
        oldFiles.setPath(f2.getAbsolutePath());

        DiffDir d = new DiffDir();
        d.diffDirsAndCountCodeChurn(oldFiles, newFiles);
        newFiles.countLines();
        newFiles.countComplexity();
        oldFiles.countLines();
        oldFiles.countComplexity();

        Reporting r = new Reporting();
        r.printReport(newFiles, true);
        System.out.println("----------------------------------------------------");
        r.printReport(oldFiles,true);
        assertNotNull("My list was empty",newFiles);
        //System.out.println(myList.size());

    }






}