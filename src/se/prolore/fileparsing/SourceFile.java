package se.prolore.fileparsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by matseriksson on 05/11/14.
 */
public abstract class SourceFile {
    protected String fileName = "";
    protected String filePath = "";
    protected String md5sum;
    // LOC Metric
    private int LOC = 0;   // Lines of Code (total)
    private int ccLOC = 0; // Lines of Comments (single line of comment
    private int trLOC = 0; // Trivial Lines ( ´{´ or ´}´)
    private int emLOC = 0; // Empty Lines (no visible characters)
    private int stLOC = 0; // Lines of Statements (ending with ';')
    private boolean isEqual = false;
    private boolean isNew = false;

    private ArrayList<Method> methodList = null;

    // Cyclomatic Complexity
    private int CC = 0;

    // Code Churn
    private int adLOC = 0; // Added Lines of Code
    private int chLOC = 0; // Changed Lines of Code
    private int dlLOC = 0; // Deleted Lines of Code


    public abstract void countComplexity();

    public abstract void getMethods();

    public abstract int getNrOfMethods();

    public abstract Method getMethod(final int index);

    public abstract float getAvgComplexity();

    public abstract String getClassOrInterface();


    public String getMd5sum() { return md5sum; }

    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean isEqual) {
        this.isEqual = isEqual;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    // Count Lines Of Code
    public void countLines() {
        try{
            FileInputStream fStream = new FileInputStream(getFilePath());
            DataInputStream dStream = new DataInputStream(fStream);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(dStream));
            String strLine;

            //Read File Line By Line
            while ((strLine = bReader.readLine()) != null) {
                strLine = strLine.trim();

                // This is a Line of Code (total)
                LOC++;

                // This is an Empty Line (no visible characters)
                if (strLine.length() == 0) {
                    emLOC++;
                }
                // Trivial Lines ( ´{´ or ´}´)
                else if (strLine.equals("{") || strLine.equals("}")) {
                    trLOC++;
                }
                // Lines of Comments (single line of comment)
                else if (strLine.startsWith("//") || strLine.startsWith("/*") || strLine.startsWith("*")) {
                    ccLOC++;
                }
                // Lines of Statements (ending with ';')
                else if (strLine.endsWith(";")) {
                    stLOC++;
                }
                // Lines of Statements (methods, functions, conditions and statements)
                else {
                    stLOC++;
                }
            }
            bReader.close();
            dStream.close();
        }catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void setFileName(final String name) {
        fileName = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(final String path) {
        filePath = path;
    }

    public String getFilePath() {
        return filePath;
    }

    // LOC Metrics
    public int getLinesOfCode() {
        return LOC;
    }

    public int getLinesOfStatements() {
        return stLOC;
    }

    public int getLinesOfComments() {
        return ccLOC;
    }

    public int getTrivialLines() {
        return trLOC;
    }

    public int getEmptyLines() {
        return emLOC;
    }


    // Source file attributes

    // Return the total number of methods
/*
    public int getNrOfMethods() {
        return methodList.size();
    }

    public Method getMethod(final int index) {
        return methodList.get(index);
    }
*/

    // Complexity mertics
    public float getComplexity() {
        return (float)CC;
    }

    public int getCCInt() {
        return CC;
    }
/*
    public float getAvgComplexity() {
        if (CC != 0) {
            return (float)CC/methodList.size();
        }
        return (float) 0;
    }
*/
    // Code Churn Metrics
    public int getAddedLines() {
        return adLOC;
    }

    public int getChangedLines() {
        return chLOC;
    }

    public int getDeletedLines() {
        return dlLOC;
    }

    public int getCodeChurn() {
        int codeChurn = adLOC + chLOC + dlLOC;
        return codeChurn;
    }

    public void setAddedLines(int value) {
        adLOC = value;
    }

    public void setChangedLines(int value) {
        chLOC = value;
    }

    public void setDeletedLines(int value) {
        dlLOC = value;
    }
}
