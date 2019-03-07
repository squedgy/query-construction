package com.dfaris.query.construction.processor;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.dfaris.query.construction.annotations.*;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;


@AutoService(Processor.class)
public class BindableProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final String packageName = "com.squedgy.query.construction.where";

        for(Element e : roundEnv.getElementsAnnotatedWith(HasBindableVersion.class)){
            TypeElement element = (TypeElement) e;

            StringBuilder builder = new StringBuilder("package ").append(packageName)
                    .append(";\npublic class Bindable").append(element.getSimpleName()).append("<Parent extends WhereParent> extends ").append(element.getQualifiedName()).append("<Parent> {\n\n");

            List<ExecutableElement> constructors = element.getEnclosedElements().stream()
                    .filter(el -> el.getKind().equals(ElementKind.CONSTRUCTOR))
                    .map(el -> (ExecutableElement) el)
                    .collect(Collectors.toList());
            constructors.forEach(con -> {

                builder.append("\t").append(con.getSimpleName()).append('(');
                Iterator<VariableElement> iterator = (Iterator<VariableElement>) con.getParameters().iterator();
                List<String> params = new LinkedList<>();
                int chr = 0;
                int idx = 0;
                while(iterator.hasNext()){
                    VariableElement element1 = iterator.next();
                    builder.append(elementUtils.getName(
                                elementUtils.getPackageOf(element1).getQualifiedName()
                                    + "."
                                    + element1.getEnclosingElement().getSimpleName()).toString()
                            )
                            .append(getChars(chr+idx))
                            .append(iterator.hasNext() ? "," : "");
                    idx++;
                }
                idx--;
                builder.append("){ super(");
                for(int i = 0; i <= idx; i++) builder.append(getChars(chr+i)).append(i == idx ? "); }\n" : ',');
            });

            builder.append("\tpublic ")
                    .append(element.getQualifiedName())
                    .append("<Parent> values(Object... constants) { return values(java.util.Arrays.asList(constants)); }\n")
                    .append("\tpublic ")
                    .append(element.getQualifiedName())
                    .append("<Parent> values(Collection<?> constants) { " +
                                    "if(constants.stream().allMatch(e -> e instanceof java.lang.String)){ " +
                                    "constants = constants.stream.map(e -> ':' + e.toString()).collect(java.util.stream.Collectors.toList()); " +
                                    "} else { " +
                                    "constants = constants.stream().map(e -> \"?\").collect(java.util.stream.Collectors.toList()); " +
                                    "} " +
                                    "constants return this.refe; " +
                                    "}\n")
                    .append("\tpublic ")
                    .append(element.getQualifiedName())
                    .append("<Parent> value(Object constant) { " +
                                    "if(constant instanceof java.lang.String) { " +
                                    "constants = java.util.Collections.singletonList(':' + constant.toString()); " +
                                    "} else {" +
                                    "constante = java.util.Collections.singletonList(\"?\"); " +
                                    "} return this.refe; }\n")
                    .append('}');

            try(Writer w = filer.createSourceFile(packageName + ".Bindable" + e.getSimpleName()).openWriter()){

            } catch (IOException e1) {
                messager.printMessage(Diagnostic.Kind.ERROR, e1.getLocalizedMessage(), e);
            }
        }
        return false;
    }

    protected String getChars(int value) {
        String ret = "";
        for(int i = 0; i <= value/26; i++){
            ret += (char) (97 + ((value/((int)Math.pow(26, i)))%26));
        }
        return ret;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    public static String getType(Class<? extends Annotation> clazz) {
        return clazz.getCanonicalName();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(
            HasBindableVersion.class.getCanonicalName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
