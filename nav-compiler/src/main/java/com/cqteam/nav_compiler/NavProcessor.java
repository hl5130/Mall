package com.cqteam.nav_compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqteam.nav_annotation.Destination;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.cqteam.nav_annotation.Destination"})
public class NavProcessor extends AbstractProcessor {

    private static final String PAGE_TYPE_ACTIVITY = "Activity";
    private static final String PAGE_TYPE_FRAGMENT = "Fragment";
    private static final String PAGE_TYPE_DIALOG = "Dialog";
    private static final CharSequence OUTPUT_FILE_NAME = "destination.json";

    // 打印日志的工具类
    private Messager messager;

    // 创建文件的
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE,"enter init....");

        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        // 获取被 Destination 标记了的页面
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Destination.class);
        if (!elements.isEmpty()) {
            // 存储页面信息，key = pageUrl， value = 页面信息
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(elements,Destination.class,destMap);

            try {
                // 创建一个文件，参数1是输出目录，参数2是包路径，参数3是文件名称
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT,"",OUTPUT_FILE_NAME);

                // StandardLocation.CLASS_OUTPUT 生成目录是 /app/build/intermediates/javac/debug/classes/目录下
                String resourcePath = resource.toUri().getPath();

                // 但是我们要生成到: app/src/main/assets/
                // 步骤1：获取assets路径
                String appPath = resourcePath.substring(0,resourcePath.indexOf("app")+4);
                String assetsPath = appPath+"src/main/assets";
                // 步骤2：创建assets文件夹
                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String content = JSON.toJSONString(destMap); // 将 HashMap 转换成 json 字符串
                // 步骤3：文件读写
                File outputFile = new File(assetsPath, OUTPUT_FILE_NAME.toString()); // 创建文件
                if (outputFile.exists()) {
                    // 如果目标文件存在，就先删除，再创建
                    outputFile.delete();
                }
                outputFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream); // 创建一个写入器
                writer.write(content); // 将 json 字符串写入文件
                writer.flush();
                fileOutputStream.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理标记的页面信息，全部收集到 HaspMap中
     * @param elements
     * @param destinationClass
     * @param destMap
     *
     *
     * Java中的 Element
     *  TypeElement             代表该元素是一个类或者接口
     *  VariableElement         代表该元素是字段、方法入参、枚举常量
     *  PackageElement          代表该元素是包级别
     *  ExecutableElement       代表方法，构造方法
     *  TypeParameterElement    代表类型、接口、方法入参的泛型参数
     */
    private void handleDestination(Set<? extends Element> elements, Class<Destination> destinationClass, HashMap<String, JSONObject> destMap) {
        elements.forEach(element -> {
            // 因为标记的是类，所以要用 TypeElement
            TypeElement typeElement = (TypeElement) element;

            // 全类名
            String clazName = typeElement.getQualifiedName().toString();
            Destination annotation = typeElement.getAnnotation(destinationClass);
            String pageUrl = annotation.pageUrl(); // 页面路由
            boolean asStarter = annotation.asStarter(); // 是否是启动页
            int id = Math.abs(clazName.hashCode()); // 获取一个Id，由全类名的hashCode生成

            // Activity、Fragment、Dialog
            String destType = getDestinationType(typeElement);

            // 判断是否有相同的pagerUrl
            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR,"不同的页面不允许使用相同的pageUrl："+pageUrl);
            } else {
                // 封装成 jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pageUrl",pageUrl); // 页面路径
                jsonObject.put("asStarter",asStarter); // 是否是启动页
                jsonObject.put("id",id); // 页面Id
                jsonObject.put("clazName",clazName); // 全类名
                jsonObject.put("destType",destType); // 页面类型
                destMap.put(pageUrl,jsonObject);
            }
        });
    }

    /**
     *  获取标记页面的类型，一般包括 Activity、Fragment、Dialog
     * @param typeElement
     * @return
     */
    private String getDestinationType(TypeElement typeElement) {
        // 获取父类
        TypeMirror typeMirror = typeElement.getSuperclass();

        // 父类的全类名
        // 例如：androidx.fragment.app.Fragment
        String superClazName = typeMirror.toString();

        if (superClazName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())){
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        } else if (superClazName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())){
            return PAGE_TYPE_FRAGMENT.toLowerCase();
        } else if (superClazName.contains(PAGE_TYPE_DIALOG.toLowerCase())){
            return PAGE_TYPE_DIALOG.toLowerCase();
        }

        // 如果这个父类的类型不是上面三种，而是类的类型或者接口类型
        if (typeMirror instanceof DeclaredType) {

            // 获取这个父类的实现类，并递归调用 getDestinationType
            Element element = ((DeclaredType) typeMirror).asElement();
            if (element instanceof TypeElement) {
                return getDestinationType((TypeElement) element);
            }
        }
        return null;
    }
}