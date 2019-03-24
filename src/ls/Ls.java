/*
 */
package ls;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author yan-7
 */
public class Ls {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Boolean isRunning = true;
       while(isRunning){
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
            if(result.equals("exit")){
                isRunning = false;
            } else{
                if(result.startsWith("ls")){
                    parseFolder(result);
                } 
                else System.out.println("Commande inconnu seule commande accepté : ls, exit");
            }
       }
    } 
    /**
     * Parse le dossier concerné
     * @param result 
     */
    private static void parseFolder(String result){
     File folder = new File(System.getProperty("user.dir"));
     String[] parameters = result.split("-");
     if (parameters.length > 2){
     System.out.println("Pour utiliser plusieur paramètres p ls -pp");
     }
     if(parameters.length == 1 || (parameters.length == 2 && isValidParameter(parameters[1].split(""))) ){
       File[] listOfFiles = folder.listFiles();
       if(listOfFiles != null){
        if(result.contains(AllowedParameter.r.toString())){
                //Inverse l'ordre d'affichage
                List<File> files = Arrays.asList(listOfFiles);
                Collections.reverse(files);
        }  
        for(File file : listOfFiles){
            System.out.print(file.getName());
            if(parameters.length > 1){
            //Si la commande à un paramètre
                String parameter = parameters[1];
                if(displayHidden(file, result)){
                //Si nous voulons afficher les fichiers cachés   
                    if(parameter.equals(AllowedParameter.R.toString()) && file.isDirectory()){
                        System.out.print(getDirectoryRecursive(file));
                    }
                    if(!parameter.contains(AllowedParameter.R.toString())){
                    //Le paramètre R ne peut pas être utilisé avec d'autre paramètres
                        if(file.isDirectory()){
                            if(parameter.contains(AllowedParameter.d.toString())){
                                System.out.print(getDirectoryAndFileCount(file));
                            }       
                        }
                        if(file.isFile()){
                            if(parameter.contains(AllowedParameter.c.toString())){
                                System.out.print( getFileLineCount(file));
                        }
                            if(parameter.contains(AllowedParameter.l.toString())){
                                System.out.print(getFileSize(file));
                            }
                        }
                    }
                }
            }
            System.out.print("\n");
        }
     }
    }else System.out.println("Paramètre inconnu");
}
    /**
     * Compte les lignes d'un fichier 
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private static int lineCount(File file) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);
        int lineNumber = 0;
        while(lnr.readLine() != null){
            lineNumber++;
        }
        return lineNumber;
        
    }/**
     * Formate l'unité de mesure 
     * @param bytes
     * @return 
     */
    public static String byteformatter(long bytes) {
    int unit = 1024;
    if (bytes < unit) return bytes + " B";
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = "KMGTPE".charAt(exp-1) + "i";
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
    
    /**
     * Vérifie si tous les paramètres correspondent bien à l'enum
     * @param parameters
     * @return 
     */
    private static Boolean isValidParameter(String[] parameters){
        String[] allowedParameters = Stream.of(AllowedParameter.values()).map(AllowedParameter::name).toArray(String[]::new);
        return Stream.of(parameters).allMatch(s -> Arrays.stream(allowedParameters).anyMatch(s :: equals));
   }
   
   /**
    * Retourne la taille du fichier
    * @param file
    * @return 
    */
   private static String getFileSize(File file){
       String fileSize = "";
       fileSize =  "size : " + byteformatter(file.length()) + " ";
       return fileSize;
   }
   /**
    * Retourne le nombre de ligne du fichier 
    * @param file
    * @return 
    */
   private static String getFileLineCount(File file){
       String lineCount = "";
       try {
             lineCount =  " number of lines :" + lineCount(file) + " ";
            } catch (IOException ex) {
                Logger.getLogger(Ls.class.getName()).log(Level.SEVERE, null, ex);
            }
       return lineCount;
   }
   
   /**
    * Affichage récursive de tous les fichiers/dossiers 
    * @param file
    * @return 
    */
   private static String getDirectoryRecursive(File file){
       String directory = "";
       File[] files =  file.listFiles();
       if(files != null){ 
       for(File f : files){
           if(f.isDirectory()){
           directory += "\n   " + f.getName() + getDirectoryRecursive(f.getAbsoluteFile());
           }
           directory += "\n    -"+ f.getName();
        }
        }
       return directory;
   }
   /**
    * Afficher les dossiers et le nombres de fichiers à l'intérieur
    * @param file
    * @return 
    */
   private static String getDirectoryAndFileCount(File file){
       String directory = "";
       int fileCount = 0;
       if(file.listFiles() != null){
        fileCount = file.listFiles().length;
       }
       directory += "  number of files: " + fileCount;
       return directory;
   }
   /**
    * Retourne le fichier/dossier caché si on a rentré le paramètre a 
    * @param file
    * @param result
    * @return 
    */
   private static Boolean displayHidden(File file, String result){
    if(file.isHidden()){
       return result.contains(AllowedParameter.a.toString());
    }
    return true;
   
   }
}
