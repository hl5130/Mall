package com.cqteam.baselibrary.nav;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqteam.baselibrary.nav.model.Destination;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * Author： 洪亮
 * Time： 2021/9/2 - 5:28 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
public class NavUtil {

    private static HashMap<String, Destination> destinations;

    /**
     *  解析assets中的文件
     * @param context
     * @param fileName
     * @return 字符串
     */
    public static String parseFile(Context context, String fileName){
        // 读取 assets 中的文件，装载到 StringBuilder 中
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            inputStream.close();
            bufferedReader.close();

            // 将 StringBuilder 中的json字符串转换成 HashMap

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  关联
     * @param activity FragmentActivity
     * @param controller NavController
     * @param containerId 宿主Id
     */
    public static void builderNavGraph(FragmentActivity activity, FragmentManager childFragmentManager, NavController controller, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();
        NavGraphNavigator graphNavigator = provider.getNavigator(NavGraphNavigator.class);
        NavGraph navGraph = new NavGraph(graphNavigator);

        // 有缓存机制的 FragmentNavigator，并注册到 NavigatorProvider 中
        HiFragmentNavigator hiFragmentNavigator = new HiFragmentNavigator(activity, childFragmentManager, containerId);
        provider.addNavigator(hiFragmentNavigator);

        // 获取页面配置信息
        String content = parseFile(activity,"destination.json");
        destinations = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>(){});
        // 对页面信息进行遍历
        // 拿到真实数据
        if (destinations != null) {
            for (Destination destination : destinations.values()) {
                switch (destination.destType) {
                    case "activity": {
                        ActivityNavigator navigator = provider.getNavigator(ActivityNavigator.class);
                        ActivityNavigator.Destination node = navigator.createDestination();
                        node.setId(destination.id);
                        node.setComponentName(new ComponentName(activity.getPackageName(), destination.clazName));
                        navGraph.addDestination(node);
                        break;
                    }
                    case "fragment": {
                        HiFragmentNavigator.Destination node = hiFragmentNavigator.createDestination();
                        node.setId(destination.id);
                        node.setClassName(destination.clazName);
                        navGraph.addDestination(node);
                        break;
                    }
                    case "dialog": {
                        DialogFragmentNavigator navigator = provider.getNavigator(DialogFragmentNavigator.class);
                        DialogFragmentNavigator.Destination node = navigator.createDestination();
                        node.setId(destination.id);
                        node.setClassName(destination.clazName);
                        navGraph.addDestination(node);
                        break;
                    }
                }

                if (destination.asStarter) {
                    navGraph.setStartDestination(destination.id);
                }
            }
        }
        controller.setGraph(navGraph);
    }

//    public static void builderBottomBar(BottomNavigationView navigationView) {
//        String  content = parseFile(navigationView.getContext(),"main_tabs_config.json");
//        BottomBar bottomBar = JSON.parseObject(content, BottomBar.class);
//
//        Menu menu = navigationView.getMenu();
//        List<BottomBar.Tab> tabs = bottomBar.tabs;
//        for (BottomBar.Tab tab: tabs) {
//            if (!tab.enable) continue;
//            Destination destination = destinations.get(tab.pageUrl);
//            if (destination != null) {
//                MenuItem menuItem = menu.add(0, destination.id, tab.index, tab.title);
//                menuItem.setIcon(R.drawable.ic_home_black_24dp);
//            }
//        }
//    }

}
