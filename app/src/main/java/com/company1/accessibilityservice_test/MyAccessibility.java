package com.company1.accessibilityservice_test;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;

@SuppressLint("NewApi")
public class MyAccessibility extends AccessibilityService {

    private static final String TAG = "MyAccessibility";
    String[] PACKAGES = {"com.android.settings"};

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "config success!");
    }

    private AccessibilityNodeInfo returnWebView(AccessibilityNodeInfo nowNode){//深度搜索返回所有子布局中的第一个WebView节点
        if(nowNode==null) return null;

        if(nowNode.getClassName().toString().equals("android.webkit.WebView")){
            return nowNode;
        }
        if(nowNode.getChildCount()==0) return null;

        int size = nowNode.getChildCount();
        AccessibilityNodeInfo webViewNode = null;
        for(int i=0;i<size;i++){
            webViewNode = returnWebView(nowNode.getChild(i));
            if(webViewNode!=null) return webViewNode;
        }
        return null;
    }
    private AccessibilityNodeInfo returnTextEqual(AccessibilityNodeInfo nowNode,String text){//深度搜索返回第一个text满足条件的子布局
        if(nowNode==null) return null;

        if(nowNode.getText().toString().equals(text)){
            return nowNode;
        }
        if(nowNode.getChildCount()==0) return null;

        int size = nowNode.getChildCount();
        AccessibilityNodeInfo textEqualNode = null;
        for(int i=0;i<size;i++){
            textEqualNode = returnTextEqual(nowNode.getChild(i),text);
            if(textEqualNode!=null) return textEqualNode;
        }
        return null;
    }
    private boolean dispatchGestureView(int x, int y) {
        boolean res = false;
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path p = new Path();
        p.moveTo(x, y);
        p.lineTo(x, y);
        builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, 100L));
        GestureDescription gesture = builder.build();
        Log.d("","点击了位置"+"("+x+","+y+")");
        sleep(200);
        res = dispatchGesture(gesture, new GestureResultCallback(){}, null);
        return res;
    }
    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        
        // TODO Auto-generated method stub
        int eventType = event.getEventType();
        String eventText = "";
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.i(TAG, "==============Start====================");
                eventText = "TYPE_VIEW_CLICKED";
                List<AccessibilityWindowInfo> windowInfo=getWindows();
                List<AccessibilityNodeInfo> nodeInfo = new ArrayList<AccessibilityNodeInfo>();
                for (AccessibilityWindowInfo accessibilityWindowInfo : windowInfo) {
                    nodeInfo.add(accessibilityWindowInfo.getRoot());
                    Log.i(TAG,"error");
                }
                List<AccessibilityNodeInfo> nodeInfoList=new ArrayList<AccessibilityNodeInfo>();
                for (AccessibilityNodeInfo accessibilityNodeInfo : nodeInfo) {
                    if(returnWebView(accessibilityNodeInfo)!=null){
                        nodeInfoList.add(returnWebView(accessibilityNodeInfo));
                    }
                    Log.i(TAG,nodeInfoList.toString());
                }
                Log.i(TAG,nodeInfoList.get(0).getChild(0).toString());
                if(returnTextEqual(nodeInfoList.get(0).getChild(0),"任务反馈")!=null)
                {
                    Log.i(TAG,returnTextEqual(nodeInfoList.get(0).getChild(0),"任务反馈").toString());
                }
                Rect rect = new Rect();
                returnTextEqual(nodeInfoList.get(0).getChild(0),"任务反馈").getBoundsInScreen(rect);
                Log.i(TAG,rect.toString());
                dispatchGestureView(rect.centerX(),rect.centerY());






//                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//                List<AccessibilityNodeInfo> nodeInfoList = nodeInfo.findAccessibilityNodeInfosByText("湘南学院");
//                for (AccessibilityNodeInfo accessibilityNodeInfo : nodeInfoList) {
//                    AccessibilityNodeInfo a=accessibilityNodeInfo.getParent();
//                    a.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//                nodeInfo = getRootInActiveWindow();
//                nodeInfoList= nodeInfo.findAccessibilityNodeInfosByText("任务反馈");
//                for (AccessibilityNodeInfo accessibilityNodeInfo : nodeInfoList) {
//                    AccessibilityNodeInfo a=accessibilityNodeInfo.getParent();
//                    Log.i(TAG, a.toString());
//                }
                Log.i(TAG, "=============END=====================");
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventText = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
        }
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

//    public boolean textClick(AccessibilityNodeInfo node) {
//
//        if (node != null) {
//            return false;
//        }
//            for (int i = 0; i <= 3; i++) {
//                if (node != null) {
//                if (node.isClickable() == false) {
//                    node = node.getParent();
//                    if (node != null) {
//                        Log.i(TAG, node.toString());
//                    }
//                    }
//                }
//            }
//            if (node.isClickable() != false) {
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }
//        return true;
//    }
}