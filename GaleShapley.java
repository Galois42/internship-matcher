

import java.util.*;
import java.io.*;

public class GaleShapley{

 static int n;
 static Stack<Integer> Sue = new Stack<Integer>(); //employeurs non jumelés
 static int[] students;
 static String[] studentName;
 static int[] employers;
 static String[] employerName;
 
 static ArrayList<HeapPriorityQueue<Integer, Integer>> PQ = new ArrayList<HeapPriorityQueue<Integer, Integer>>();
 static int[][] A;


 /* Lire le fichier d'entrée et effectuer toutes les étapes d'initialisation */
 public static void initialize(String filename) throws Exception
 {
  File directory = new File("");
  filename = directory.getCanonicalPath()+"/"+filename;


  FileReader fr1 = new FileReader(filename);
  BufferedReader reader1 = new BufferedReader(fr1);

  String num = readLine(1, reader1);
  n = Integer.parseInt(num);
 
  students  = new int[n];
  employers = new int[n];
  studentName  = new String[n];
  employerName = new String[n];
  A = new int[n][n];


  // lire et initialiser les informations des employeurs et des étudiants
  for(int i=0; i<n; i++)
  {
   int line_employer = i+2;
   int line_student  = i+2+n;

   FileReader fr2 = new FileReader(filename);
   BufferedReader reader2 = new BufferedReader(fr2);

   FileReader fr3 = new FileReader(filename);
   BufferedReader reader3 = new BufferedReader(fr3);

   String eachEmployerName = readLine(line_employer, reader2);
   String eachStudentName = readLine(line_student, reader3);
   
   employerName[i] = eachEmployerName;
   employers[i] = -1;
   Sue.push(i);

   studentName[i] = eachStudentName;
   students[i] = -1;
  }

  // lire et initialiser les informations de classement dans PQ et A
  for(int i=0; i<n; i++)
  {
   int line = i+2*n+2;

   FileReader fr4 = new FileReader(filename);
   BufferedReader reader4 = new BufferedReader(fr4);
   
   String rankPairs = readLine(line, reader4);

   String[] splitedRankPair = rankPairs.split("\\s+");
   
   HeapPriorityQueue<Integer,Integer> pq = new HeapPriorityQueue<>(n);
   
   for(int j=0; j<n; j++)
   {
    String[] splitedRankNum = splitedRankPair[j].split(",");

    int num1 = Integer.parseInt(splitedRankNum[0]);
    pq.insert(num1, j);
      
    int num2 = Integer.parseInt(splitedRankNum[1]);
    A[j][i] = num2;
   }
   
   PQ.add(pq); 

  }
 }

 /* Lire des lignes spécifiques dans le fichier *.txt */
 private static String readLine(int lineNumber, BufferedReader reader) throws Exception{
  String line = "";
  String text = "";
  int tmpCount=0;

  while((line = reader.readLine()) != null)
  {
   tmpCount++;
   if(tmpCount == lineNumber)
   {
    text = line;
   }
  }

  return text;
 }


 /* Exécuter l'algorithme Gale-Shapley */
 public static void execute(){
  while(!Sue.empty())
  {
   int e = Sue.pop(); 
   
   Entry<Integer,Integer> pairMin = (PQ.get(e)).removeMin();
   int s = pairMin.getValue(); 
   
   int eStudent = students[s];
   
   
   if(eStudent == -1) 
   {
    
    students[s] = e;
    employers[e] = s; 
   }
   
   
   else if(A[s][e] < A[s][eStudent])
   {
    students[s] = e;
    employers[e] = s;  
    employers[eStudent] = -1; // non jumelé
    Sue.push(eStudent);
   }

   else
   {
    Sue.push(e);
   } 
  }
 }

 /* Enregistrer les résultats dans un fichier texte */
 public static void save(String filename) throws Exception{
  String content = "";

  String txtName = filename;
  File directory = new File("");
  filename = directory.getCanonicalPath()+"/"+filename;
  
  File file = new File(filename);
  File newfile = new File(file.getParent() + File.separator + "matches_" + txtName);
  
  // Créer le fichier de sortie
  if(!newfile.exists())
  {
   newfile.createNewFile();
  }
  
  FileWriter fw = new FileWriter(newfile.getAbsoluteFile());
  BufferedWriter bw = new BufferedWriter(fw);

  // Écrire les résultats dans le fichier de sortie
  for(int i=0; i<n; i++)
  {
   String str = String.valueOf(i);
   int index = employers[i];
   content = "Match " + str + ": " + employerName[i] + " - " + studentName[index] + "\n"; 
   bw.write(content);
  }

     bw.close();
 }

 /* Demande un nom de fichier, appelle la méthode initialize, execute et save */
 public static void main(String[] args) throws Exception
 {
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  System.out.println("Veuillez entrer le nom du fichier: ");
  String filename = br.readLine();
  
  initialize(filename);
  execute();
  save(filename);
 }
}