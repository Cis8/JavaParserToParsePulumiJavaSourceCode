package ASTawsEc2Analyzer.Parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListMethodsNames {
    public static void listMethods(File projectDir) {
        Hashtable<String, LinkedList<String>> classes_methods = new Hashtable<>();

        // Set to analyze Vpn class only for now
        new DirExplorer((level, path, file) -> path.endsWith("Args.java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        if(!n.getName().toString().equals("Builder")) {
                            List<MethodDeclaration> extracted_methods;
                            extracted_methods = n.getMethods();
                            extracted_methods.stream()
                                    .forEach(x -> addToHashtable(x.getName().toString(), n.getName().toString(), classes_methods));
                            //System.out.println(" * ").toString());
                        }
                    }
                }.visit(StaticJavaParser.parse(file), null);

                System.out.println( ); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);

        classes_methods.forEach((k, li) -> System.out.println(k + ": " + classes_methods.get(k).toString()));
    }

    private static void addToHashtable(String method_name, String class_name, Hashtable<String, LinkedList<String>> dict){
        if(dict.containsKey(method_name)) {
            dict.get(method_name).add(class_name);
        }
        else{
            LinkedList<String> newList = new LinkedList<>();
            newList.add(class_name);
            dict.put(method_name, newList);
        }
    }
}
