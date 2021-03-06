import se.prolore.fileparsing.SourceFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by matseriksson on 25/09/14.
 */
public class Reporting {

    public void writeAggregatedReport(SourceFiles newFiles, boolean includeCodeChurn) {
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("aggregated.csv");
            out = new BufferedWriter(fstream);

            out.write("Total (Aggregated Metrics)");
            out.write(System.getProperty("line.separator")+"Total Lines of Code;" + newFiles.getLinesOfCode());
            out.write(System.getProperty("line.separator")+"Executable Lines;" + newFiles.getLinesOfStatements());
            out.write(System.getProperty("line.separator")+"Lines of Comments;" + newFiles.getLinesOfComments());
            out.write(System.getProperty("line.separator")+"Trivial Lines;" + newFiles.getTrivialLines());
            out.write(System.getProperty("line.separator")+"Empty Lines;" + newFiles.getEmptyLines());
            out.write(System.getProperty("line.separator")+"Code Complexity;" + newFiles.getComplexity());
            out.write(System.getProperty("line.separator")+"Number of Files;" + newFiles.getNrOfFiles());
            out.write(System.getProperty("line.separator")+"Average File Complexity;" + newFiles.getAvgComplexity());
            out.write(System.getProperty("line.separator")+"Comment Percentage;" + (100 * newFiles.getLinesOfComments()) / newFiles.getLinesOfCode() + "%");
            // Recommendations: Code where the percentage of comment is lower than 20% should be more commented.
            // However overly commented code (>40%) is more difficult to read.
            if (includeCodeChurn) {
                out.write(System.getProperty("line.separator")+"Added Lines of Code;" + newFiles.getAddedLines());
                out.write(System.getProperty("line.separator")+"Changed Lines of Code;" + newFiles.getChangedLines());
                out.write(System.getProperty("line.separator")+"Deleted Lines of Code;" + newFiles.getDeletedLines());
                out.write(System.getProperty("line.separator")+"Code Churn;" + newFiles.getCodeChurn());
            }
            //Close the output stream
            out.close();
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }


    public void writeReport(SourceFiles newFiles, boolean includeCodeChurn) {
        writeReport(newFiles,includeCodeChurn,"out.csv");
    }

    public void writeReport(SourceFiles newFiles, boolean includeCodeChurn, String fileName) {
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter(fileName );
            out = new BufferedWriter(fstream);
            out.write("File Path;");
            out.write("File Name;");
            out.write("Total Lines of Code;");
            out.write("Executable Lines;");
            out.write("Lines of Comments;");
            out.write("Trivial Lines;");
            out.write("Empty Lines;");
            out.write("Code Complexity;");
            out.write("Number of Methods;");
            out.write("Average Method Complexity;");
            out.write("Comment Percentage;");
            if (includeCodeChurn) {
                out.write("Added Lines of Code;");
                out.write("Changed Lines of Code;");
                out.write("Deleted Lines of Code;");
                out.write("Code Churn;");
                out.write("Equal;");
                out.write("New file;");
            }
            out.write(System.getProperty("line.separator"));

            for (int i = 0; i < newFiles.getNrOfFiles(); i++) {
                out.write(newFiles.getSrcFile(i).getFilePath() + ";");
                out.write(newFiles.getSrcFile(i).getFileName() + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getLinesOfCode()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getLinesOfStatements()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getLinesOfComments()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getTrivialLines()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getEmptyLines()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getCCInt()) + ";");
                out.write(String.valueOf(newFiles.getSrcFile(i).getNrOfMethods()) + ";");

                // needed to output the float with a , instead of . for Numbers on my Mac
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
                DecimalFormat df = (DecimalFormat)nf;
                String output = df.format(newFiles.getSrcFile(i).getAvgComplexity());
                out.write(output + ";");
                //out.write(String.valueOf(newFiles.getSrcFile(i).getAvgComplexity()) + ";");


                out.write(String.valueOf((100 * newFiles.getSrcFile(i).getLinesOfComments()) / newFiles.getSrcFile(i).getLinesOfCode() + "%") + ";");
                if (includeCodeChurn) {
                    out.write(String.valueOf(newFiles.getSrcFile(i).getAddedLines()) + ";");
                    out.write(String.valueOf(newFiles.getSrcFile(i).getChangedLines()) + ";");
                    out.write(String.valueOf(newFiles.getSrcFile(i).getDeletedLines()) + ";");
                    out.write(String.valueOf(newFiles.getSrcFile(i).getCodeChurn()) + ";");
                    out.write(String.valueOf(newFiles.getSrcFile(i).isEqual()) + ";");
                    out.write(String.valueOf(newFiles.getSrcFile(i).isNew()) + ";");
                }
                out.write(System.getProperty("line.separator"));
            }

            //Close the output stream
            out.close();
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }



    public void printReport(SourceFiles newFiles, boolean includeCodeChurn) {
        for (int i = 0; i < newFiles.getNrOfFiles(); i++) {

            System.out.println(newFiles.getSrcFile(i).getFilePath());
            System.out.println("\t Total Lines of Code:       " + newFiles.getSrcFile(i).getLinesOfCode());
            System.out.println("\t Executable Lines:          " + newFiles.getSrcFile(i).getLinesOfStatements());
            System.out.println("\t Lines of Comments:         " + newFiles.getSrcFile(i).getLinesOfComments());
            System.out.println("\t Trivial Lines:             " + newFiles.getSrcFile(i).getTrivialLines());
            System.out.println("\t Empty Lines:               " + newFiles.getSrcFile(i).getEmptyLines());
            System.out.println("\t Code Complexity:           " + newFiles.getSrcFile(i).getCCInt());
            System.out.println("\t Number of Methods:         " + newFiles.getSrcFile(i).getNrOfMethods());
            System.out.println("\t Average Method Complexity: " + newFiles.getSrcFile(i).getAvgComplexity());
            System.out.println("\t Comment Percentage:        " + (100 * newFiles.getSrcFile(i).getLinesOfComments()) / newFiles.getSrcFile(i).getLinesOfCode() + "%");
            // Recommendations: Code where the percentage of comment is lower than 20% should be more commented.
            // However overly commented code (>40%) is more difficult to read.
            if (includeCodeChurn) {
                if (newFiles.getSrcFile(i).isEqual()) {
                    System.out.println("\t Files are identical:       " + newFiles.getSrcFile(i).isEqual());
                }
                if (newFiles.getSrcFile(i).isNew()) {
                    System.out.println("\t >> New file:               " + newFiles.getSrcFile(i).isNew());
                }
                System.out.println("\t Added Lines of Code:       " + newFiles.getSrcFile(i).getAddedLines());
                System.out.println("\t Changed Lines of Code:     " + newFiles.getSrcFile(i).getChangedLines());
                System.out.println("\t Deleted Lines of Code:     " + newFiles.getSrcFile(i).getDeletedLines());
                System.out.println("\t Code Churn:                " + newFiles.getSrcFile(i).getCodeChurn());

            }
        }
    }

    public void printOverallStatistics(SourceFiles newFiles, boolean includeCodeChurn) {

        System.out.println("Total (Aggregated Metrics)");
        System.out.println("\t Total Lines of Code:     " + newFiles.getLinesOfCode());
        System.out.println("\t Executable Lines:        " + newFiles.getLinesOfStatements());
        System.out.println("\t Lines of Comments:       " + newFiles.getLinesOfComments());
        System.out.println("\t Trivial Lines:           " + newFiles.getTrivialLines());
        System.out.println("\t Empty Lines:             " + newFiles.getEmptyLines());
        System.out.println("\t Code Complexity:         " + newFiles.getComplexity());
        System.out.println("\t Number of Files:         " + newFiles.getNrOfFiles());
        System.out.println("\t Average File Complexity: " + newFiles.getAvgComplexity());
        System.out.println("\t Comment Percentage:      " + (100 * newFiles.getLinesOfComments()) / newFiles.getLinesOfCode() + "%");
        // Recommendations: Code where the percentage of comment is lower than 20% should be more commented.
        // However overly commented code (>40%) is more difficult to read.
        if (includeCodeChurn) {
            System.out.println("\t Added Lines of Code:     " + newFiles.getAddedLines());
            System.out.println("\t Changed Lines of Code:   " + newFiles.getChangedLines());
            System.out.println("\t Deleted Lines of Code:   " + newFiles.getDeletedLines());
            System.out.println("\t Code Churn:              " + newFiles.getCodeChurn());
        }
    }
}
