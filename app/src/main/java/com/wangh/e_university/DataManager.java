package com.wangh.e_university;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataManager {
    private final static int CLASS_TABLE = 0;
    private final static int SCORE = 1;
    private final static int EXAM = 2;
    private final static int PUBLIC_CLASSES = 3;
    private final static int CURRENT_NUMBER = 4;
    private final static int INFO = 5;
    private final static String[] CLASS_START_TIME = {"8:00", "08:55", "10:10", "11:05", "14:00", "14:55", "16:10", "17:05", "18:30", "19:25", "20:20"};
    private final static String[] CLASS_END_TIME = {"08:45", "09:40", "10:55", "11:50", "14:45", "15:40", "16:55", "17:50", "19:15", "20:10", "21:05"};
    private final static Map<String, String> CLASS_SORT = new HashMap<String, String>() {{
        put("99", "跨选类");
        put("01", "公共基础课");
        put("02", "学科基础课");
        put("03", "集中性实践教学环节");
        put("04", "专业课");
    }};
    private final static Map<String, String> CLASS_NATURE = new HashMap<String, String>() {{
        put("99", "跨选课");
        put("01", "必修课");
        put("02", "选修课");
    }};
    private final static Map<String, String> CLASS_CATEGORY = new HashMap<String, String>() {{
        put("1", "艺术公选");
        put("2", "其他公选");
        put("3", "经管公选");
        put("4", "人文公选");
        put("5", "其他");
        put("6", "心理公选");
        put("7", "电职公选");
        put("8", "渤职公选");
    }};
    private final static Map<String, String> CLASS_DEPARTMENT = new HashMap<String, String>() {{
        put("01", "机械工程学院");
        put("03", "材料科学与工程学院");
        put("04", "自动化学院");
        put("05", "电子信息工程学院");
        put("06", "计算机与通信工程学院");
        put("07", "化学化工学院");
        put("08", "海运学院");
        put("09", "管理学院");
        put("10", "外国语学院");
        put("101", "党委办公室、校长办公室");
        put("102", "党委组织部、党校、党委统战部");
        put("103", "党委宣传部");
        put("104", "纪委监察室");
        put("105", "审计处");
        put("106", "党委学工部、学生处（学生就业指导中心）、人民武装部");
        put("107", "党委保卫部、保卫处");
        put("108", "离退休人员工作办公室");
        put("109", "机关党委");
        put("11", "法政学院");
        put("110", "人事处");
        put("111", "教务处（招生工作办公室）");
        put("112", "科学技术处（学刊编辑部）");
        put("113", "研究生部（学科建设办公室）");
        put("114", "国际交流处（国际教育学院、国际合作办公室）、港澳台事务办公室");
        put("115", "财务处");
        put("116", "国有资产及校园经济管理处");
        put("117", "后勤管理处（校园卡中心、后勤集团）");
        put("118", "基建处");
        put("119", "工会");
        put("12", "艺术学院");
        put("120", "团委");
        put("121", "继续教育学院");
        put("122", "档案馆");
        put("123", "招聘相关部门");
        put("13", "环境科学与安全工程学院");
        put("15", "理学院");
        put("16", "国际工商学院");
        put("17", "聋人工学院");
        put("18", "职业技术学院");
        put("19", "华信软件学院");
        put("25", "学生工作办公室");
        put("28", "体育教学部");
        put("29", "工程训练中心");
        put("30", "图书馆");
        put("31", "教务处");
        put("32", "卫生室");
        put("33", "武装部");
        put("34", "艺术教育中心");
        put("35", "心理咨询中心");
        put("36", "就业分配办公室");
        put("45", "大学软件学院");
        put("46", "电子信息职业技术学院");
        put("47", "天津渤海职业技术学院");
        put("49", "研究生学院");
        put("58", "汉语言文化学院");
        put("66", "计算机与通信工程学院(中加)");
        put("99", "宣传部");
    }};
    private final static Map<String, String> MAJOR = new HashMap<String, String>() {{
        put("0101", "机械工程及自动化");
        put("0102", "过程装备与控制工程");
        put("0103", "工业设计(造型设计)");
        put("0104", "机械电子工程");
        put("0105", "机械工程及自动化(卓越班)");
        put("0106", "机械工程");
        put("0107", "机械工程（卓越）");
        put("0191", "机械类");
        put("0301", "材料科学与工程");
        put("0302", "材料成型及控制工程");
        put("0303", "材料物理");
        put("0304", "材料化学");
        put("0305", "功能材料");
        put("0391", "材料科学类");
        put("0392", "材料类");
        put("0401", "测控技术与仪器");
        put("0402", "热能与动力工程");
        put("0403", "电气工程及其自动化");
        put("0404", "自动化");
        put("0405", "能源动力系统及自动化");
        put("0406", "自动化卓越");
        put("0407", "新能源科学与工程");
        put("0408", "能源与动力工程");
        put("0491", "能源动力类");
        put("0492", "仪器类");
        put("0501", "光信息科学与技术");
        put("0502", "电子信息工程");
        put("0503", "电子科学与技术");
        put("0504", "电子信息科学与技术");
        put("0506", "集成电路设计与集成系统");
        put("0507", "光电信息科学与工程");
        put("0508", "电子信息工程（卓越）");
        put("0509", "集成电路设计与集成系统（卓越）");
        put("0591", "电子信息类");
        put("0601", "信息与计算科学");
        put("0602", "计算机科学与技术");
        put("0603", "信息安全");
        put("0604", "通信工程");
        put("0605", "网络工程");
        put("0606", "物联网工程");
        put("0607", "计算机科学与技术（中加）");
        put("0701", "应用化学(理)");
        put("0702", "化学工程与工艺");
        put("0703", "制药工程");
        put("0704", "生物工程");
        put("0705", "应用化学(工)");
        put("0706", "药学");
        put("0791", "化工与制药类");
        put("0801", "航海技术");
        put("0802", "轮机工程");
        put("0804", "轮机工程");
        put("0805", "船舶电子电气工程");
        put("0901", "信息管理与信息系统");
        put("0902", "工业工程");
        put("0903", "工商管理");
        put("0904", "工程管理");
        put("0906", "保险");
        put("0907", "工程造价");
        put("0908", "广告学");
        put("0909", "物流管理");
        put("0910", "财务管理");
        put("0911", "交通运输");
        put("0912", "市场营销");
        put("0913", "保险学");
        put("0991", "管理科学与工程类");
        put("0992", "工商管理类");
        put("1001", "英语");
        put("1002", "日语");
        put("1101", "社会工作");
        put("1103", "社会学");
        put("1201", "艺术设计");
        put("1202", "工业设计");
        put("1203", "装饰艺术");
        put("1204", "摄影");
        put("1205", "动画");
        put("1206", "动画(中加)");
        put("1207", "视觉传达设计");
        put("1208", "环境设计");
        put("1209", "产品设计");
        put("1301", "环境工程");
        put("1302", "安全工程");
        put("1303", "环境科学");
        put("1304", "资源环境与城乡规划管理");
        put("1305", "自然地理与资源环境");
        put("1306", "资源循环科学与工程");
        put("1391", "环境与安全类");
        put("1392", "环境科学与工程类");
        put("1393", "安全科学与工程类");
        put("1501", "应用物理学");
        put("1502", "数学与应用数学");
        put("1601", "工商管理(国工)");
        put("1603", "工业工程(物流管理方向)");
        put("1604", "工商管理（国工3+1）");
        put("1701", "计算机科学与技术(聋工)");
        put("1702", "艺术设计");
        put("1703", "艺术设计");
        put("1704", "产品设计");
        put("1705", "服装与服饰设计");
        put("1706", "自动化(聋工全纳)");
        put("1707", "电子信息工程(聋工全纳)");
        put("1708", "工程造价(聋工全纳)");
        put("1709", "财务管理(聋工全纳)");
        put("1710", "网络工程（聋工）");
        put("1804", "工商管理(物流管理)(专科起点)");
        put("1807", "材料成型及控制工程(专科起点)");
        put("1901", "软件工程");
        put("3801", "英语(国际教育学院)");
        put("3802", "通信工程(国际教育学院)");
        put("3803", "电子信息工程(国际教育学院)");
        put("3804", "艺术设计(国际教育学院)");
        put("3805", "机械工程及自动化(国际教育学院)");
        put("3806", "信息安全(国际教育学院)");
        put("3808", "汉语言文学(国际教育学院)");
        put("3809", "计算机科学与技术(国际教育学院)");
        put("3810", "工业工程(国际教育学院)");
        put("3811", "电气工程及其自动化(国际教育学院)");
        put("3812", "工商管理类(国际教育学院)");
        put("3813", "日语(国际教育学院)");
        put("4601", "电子信息工程");
        put("4701", "化学工程与工艺");
        put("5801", "英语(国际教育)");
        put("5802", "通信工程(国际教育)");
        put("5803", "电子信息工程(国际教育)");
        put("5804", "艺术设计(国际教育)");
        put("5805", "机械工程及自动化(国际教育)");
        put("5806", "信息安全(国际教育)");
        put("5809", "计算机科学与技术(国际教育)");
        put("5810", "工业工程(国际教育)");
        put("5811", "电气工程及其自动化(国际教育)");
        put("5812", "工商管理类(国际教育)");
        put("5813", "日语(国际教育)");
        put("5814", "物流管理(国际教育)");
        put("5815", "财务管理(国际教育)");
        put("5816", "工商管理(国际教育)");
        put("5817", "工程管理(国际教育)");
        put("5818", "汉语言文学");
        put("5821", "过程装备与控制工程(国际教育)");
        put("5822", "汉语言文学(国际教育)");
    }};

    private SharedPreferences sharedPreferences;
    private DatabaseManager databaseManager;
    private Context context;
    private ChoosingClassesFragment targetFrament;
    private Date date;
    private int classCount;
    private ArrayList<String> classTitles;
    private ArrayList<Integer> classColors;
    private ArrayList<ClassForChoose> classForChooses;
    private boolean updated;
    private boolean classGot;
    private ClassesCurrentNumberUpdater currentNumberUpdater;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLASS_TABLE:
                    doUpdateClassTable((Document) msg.obj);
                    break;
                case SCORE:
                    doUpdateScore((Document) msg.obj);
                    break;
                case EXAM:
                    doUpdateExam((Document) msg.obj);
                    break;
                case PUBLIC_CLASSES:
                    doUpdatePublicClasses((Document) msg.obj);
                    break;
                case CURRENT_NUMBER:
                    doUpdateCurrentNumber((String) msg.obj);
                    break;
                case INFO:
                    doUpdateInfo((Document)msg.obj);
                    break;
            }
        }
    };

    public interface updateListener{
        void update();
    }

    public DataManager(Context context) {
        currentNumberUpdater = new ClassesCurrentNumberUpdater();
        this.context = context;
    }

    public void updateInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequester httpRequester = new HttpRequester();
                Message msg= new Message();
                msg.what=INFO;
                msg.obj=httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/xjgl/jbxx.do");
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doUpdateInfo(Document doc){
        sharedPreferences = context.getSharedPreferences("account", Context.MODE_APPEND);
        Element element = doc.getElementById("yxdm");
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Log.d("doUpdateInfo",CLASS_DEPARTMENT.get(element.val()));
        editor.putString("department",CLASS_DEPARTMENT.get(element.val()));
        element = doc.getElementById("zydm");
//        Log.d("doUpdateInfo",MAJOR.get(element.val()));
        editor.putString("major",MAJOR.get(element.val()));
        editor.apply();
    }

    public void updatePublicClasses(ChoosingClassesFragment fragment) {
        Log.d("get public classes", "start");
        updated = false;
        targetFrament=fragment;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequester httpRequester = new HttpRequester();
                httpRequester.setChoose(true);
                Message msg = new Message();
                msg.what = PUBLIC_CLASSES;
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/index.xk", "http://xk.tjut.edu.cn/xsxk/logout.xk", false);
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/main.xk", "http://xk.tjut.edu.cn/xsxk/", false);
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/loadData.xk?method=getXsLoginCnt", "http://xk.tjut.edu.cn/xsxk/main.xk", true);
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/loadData.xk?method=getXksj", "http://xk.tjut.edu.cn/xsxk/main.xk", false);
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/loadData.xk?method=loginCheck", "http://xk.tjut.edu.cn/xsxk/index.xk", false);
                httpRequester.get("http://xk.tjut.edu.cn/xsxk/xkjs.xk?pyfaid=01777&jxqdm=1&data-frameid=main&data-timer=2000&data-proxy=proxy.xk", "http://xk.tjut.edu.cn/xsxk/index.xk", false);
                msg.obj = httpRequester.get("http://xk.tjut.edu.cn/xsxk/qxgxk.xk", "http://xk.tjut.edu.cn/xsxk/xkjs.xk?pyfaid=01482&jxqdm=1&data-frameid=main&data-timer=2000&data-proxy=proxy.xk", true);
                handler.sendMessage(msg);
//                msg = new Message();
//                msg.what = PUBLIC_CLASSES;
//                msg.obj = httpRequester.getString("http://xk.tjut.edu.cn/xsxk/loadData.xk?method=gerJxbXkrs");
//                handler.sendMessage(msg);
            }
        }).start();
    }

    public ArrayList<ClassForChoose> getClassForChooses() {
        return classForChooses;
    }

    public boolean isClassGot() {
        return classGot;
    }

    private void doUpdatePublicClasses(Document doc) {
        Element line;
        Elements classes = doc.getElementsByTag("script");
        String[] lines;
        String[] temp;
        ClassForChoose classForChoose;
        classForChooses = new ArrayList<ClassForChoose>();
        int classCount = 0;

        for (int i = 0; i < classes.size(); i++) {
            line = classes.get(i);
//            Log.d("class for choose",line.toString());
//            Log.d("class for choose",line.attr("src"));
            if (!line.hasAttr("src")) {
                lines = line.toString().split("'");
//                Log.d("class for choose",line.toString());
//                Log.d("line length",lines.length+"");
                if (lines.length == 51) {
                    Log.d("lines", line.toString());
                    Log.d("lines", lines[1]);
                    Log.d("lines", lines[3]);
                    Log.d("lines", lines[5]);
                    Log.d("lines", lines[29]);
                    Log.d("lines", lines[33]);
                    Log.d("lines", lines[45]);

                    classCount++;
                    classForChoose = new ClassForChoose();
                    classForChoose.setIdForChoose(lines[3]);
                    classForChoose.setId(lines[5]);
                    classForChoose.setTitle(lines[7]);
                    classForChoose.setHours(lines[13]);
                    classForChoose.setSort(CLASS_SORT.get(lines[21]));
                    classForChoose.setNature(CLASS_NATURE.get(lines[23]));
                    classForChoose.setDepartment(CLASS_DEPARTMENT.get(lines[27]));
                    try {
                        classForChoose.setCredit(Integer.parseInt(lines[29]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    classForChoose.setTeacher(lines[31]);
                    try {
                        classForChoose.setMaximumNumber(Integer.parseInt(lines[33]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    classForChoose.setCategory(CLASS_CATEGORY.get(lines[45]));
                    classForChooses.add(classForChoose);
                } else {
                    Log.d("lines", line.toString());
                    Log.d("lines", lines[7]);
                    Log.d("lines", lines[9]);
                    Log.d("lines", lines[11]);
                    classForChoose = classForChooses.get(classCount - 1);
                    classForChoose.getWeek().add(lines[5]);
                    try {
                        classForChoose.getData().add(Integer.parseInt(lines[7]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    try {
                        classForChoose.getTimeStart().add(Integer.parseInt(lines[9]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    try {
                        classForChoose.getTimeEnd().add(Integer.parseInt(lines[11]));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    classForChoose.getLocation().add(lines[13]);
                }
                Log.d("lines", classForChooses.get(classCount-1).toString());
            }
        }
        for (int i = 0; i < classForChooses.size(); i++) {
            Log.d("got class for choose", classForChooses.get(i).toString());
        }
//        updated=true;
//        notifyAll();
        currentNumberUpdater.update();
    }

    public void updateCurrentNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequester httpRequester = new HttpRequester();
                Message msg = new Message();
                httpRequester.setChoose(true);
                msg.what = CURRENT_NUMBER;
                msg.obj = httpRequester.getString("http://xk.tjut.edu.cn/xsxk/loadData.xk?method=gerJxbXkrs");
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doUpdateCurrentNumber(String date) {
        Log.d("doUpdateCurrentNumber", date);
        ClassForChoose classForChoose;
        String lines[] = date.split("\\{")[2].replace("}", "").replace("\\", "").replace("\"", "").split(",");
        for (int i = 0; i < classForChooses.size(); i++) {
            classForChoose = classForChooses.get(i);
            for (int j = 0; j < lines.length; j++) {
                //Log.d("number got",lines[j].split(":")[0]);
                if (classForChoose.getIdForChoose().equals(lines[j].split(":")[0])) {
                    classForChoose.setCurrentNumber(Integer.parseInt(lines[j].split(":")[1]));
                    Log.d("number got", classForChoose.getCurrentNumber() + "");
                }
            }
        }
        targetFrament.update();
    }

    public void updateExam() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequester httpRequester = new HttpRequester();
                Message msg = new Message();
                date = new Date();
                String postContent = "xnxqdm=";
                if (date.getMonth() <= 8 && date.getMonth() >= 2) {
                    postContent += (date.getYear() - 1) + "-" + date.getYear() + "-2";
                } else if(date.getMonth()>8){
                    postContent += date.getYear() + "-" + (date.getYear() + 1) + "-1";
                } else if(date.getMonth()<2){
                    postContent += (date.getYear()-1) + "-" + date.getYear() + "-1";
                }
                msg.what = EXAM;
                msg.obj = httpRequester.post("http://ssfw.tjut.edu.cn/ssfw/xsks/kcxx.do", postContent, true);
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doUpdateExam(Document doc) {
        databaseManager = new DatabaseManager(context);
        databaseManager.deleteAllExam();
        Element table = doc.getElementsByTag("tbody").get(2);
        ExamItem aExamItem;

//        Log.d("exam",table.toString());
        table.child(0).remove();

        while (!table.children().isEmpty()) {
            aExamItem = new ExamItem();
            aExamItem.setTitle(table.child(0).child(2).text());
            aExamItem.setTeacher(table.child(0).child(4).text());
            aExamItem.setCredit(Integer.parseInt(table.child(0).child(5).text()));
            aExamItem.setTime(table.child(0).child(6).text());
            aExamItem.setLocation(table.child(0).child(7).text());
            aExamItem.setModus(table.child(0).child(8).text());
            table.child(0).remove();
            Log.d("got exam", aExamItem.toString());
            databaseManager.addExam(aExamItem);
        }
        databaseManager.closeDB();
    }

    public void updateScore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequester httpRequester = new HttpRequester();
                Message msg = new Message();
                date = new Date();
                String postContent = "qXndm_ys=";
                if (date.getMonth() <= 8 && date.getMonth() >= 2) {
                    postContent += (date.getYear() - 1) + "-" + date.getYear();
                } else if(date.getMonth()>8){
                    postContent += date.getYear() + "-" + (date.getYear() + 1);
                } else if(date.getMonth()<2){
                    postContent += (date.getYear()-1) + "-" + date.getYear();
                }
                postContent += "&qXqdm_ys=";
                if (date.getMonth() <= 8 && date.getMonth() >= 2) {
                    postContent += 2;
                } else {
                    postContent += 1;
                }
                postContent += "&";
                msg.what = 1;
                httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/zhcx/cjxx.do");
                msg.obj = httpRequester.post("http://ssfw.tjut.edu.cn/ssfw/zhcx/cjxx.do", "opytpe=query&" +
                        "isFirst=1&" +
                        postContent +
                        "qKclbdm_ys=&" +
                        "qKch_ys=&" +
                        "qKcm_ys=&" +
                        "currentSelectTabId=01", true);
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void doUpdateScore(Document doc) {
        databaseManager = new DatabaseManager(context);
        Element table = doc.getElementsByTag("tbody").get(7);
        if(table!=null){
            databaseManager.deleteAllScore();

            ScoreItem aScoreItem;

            table.child(0).remove();
//        Log.d("score",table.toString());
            if (table.child(0).child(0).text().equals("暂无记录")) {
                Log.d("doUpdateScore", "no score");
            } else while (!table.children().isEmpty()) {
                Log.d("got average credit", doc.getElementsByClass("ui_alert").first().child(0).text());
                databaseManager.updateAverageCredit(doc.getElementsByClass("ui_alert").first().child(0).text());
                aScoreItem = new ScoreItem();
                aScoreItem.setTitle(table.child(0).child(3).text());
                aScoreItem.setCredit(Double.parseDouble(table.child(0).child(7).text().replace("\n", "").replace(" ", "")));
                aScoreItem.setScore(Double.parseDouble(table.child(0).child(8).child(0).child(0).text().replace("&nbsp;", "").replace("\n", "").replace(" ", "")));
                table.child(0).remove();
                Log.d("got Score", aScoreItem.toString());
                databaseManager.addScore(aScoreItem);
            }
        }


        databaseManager.closeDB();
    }

    public void updateClassTable() {
        Log.d("updateClassTable", "start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                    HttpRequester httpRequester = new HttpRequester();
                    Message msg = new Message();
                    date = new Date();
                    String urlContent = "";
                    if (date.getMonth() <= 8 && date.getMonth() >= 2) {
                        urlContent += (date.getYear() - 1) + "-" + date.getYear() + "-2";
                    } else if(date.getMonth()>8){
                        urlContent += date.getYear() + "-" + (date.getYear() + 1) + "-1";
                    } else if(date.getMonth()<2){
                        urlContent += (date.getYear()-1) + "-" + date.getYear() + "-1";
                    }

                    msg.what = CLASS_TABLE;
                    msg.obj = httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/pkgl/kcbxx/4/" + urlContent + ".do");
//                    msg.obj = httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/pkgl/kcbxx/4/2016-2017-1.do");
                    handler.sendMessage(msg);
            }
        }).start();
    }

    private void doUpdateClassTable(Document doc) {
        databaseManager = new DatabaseManager(context);
        databaseManager.deleteAllClass();
        classTitles = new ArrayList<String>();
        classColors = new ArrayList<Integer>();
        Log.d("updateClassTable", "page got");
        Element table = doc.getElementsByTag("tbody").first();
        Boolean[][] haveClass = new Boolean[7][11];
        Element line;
        Element aClass;
        int classLanght;
        String tempText;
        int offset;
        classCount = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 11; j++) {
                haveClass[i][j] = false;
            }
        }

        for (int i = 1; i <= 11; i++) {
            line = table.child(i);
            line.child(0).remove();
            line.child(0).remove();
            offset = 0;
            for (int j = 1; j <= 7; j++) {
                if (haveClass[j - 1][i - 1]) {
                    offset++;
                    continue;
                }

                aClass = line.child(j - 1 - offset);
                if (!aClass.toString().equals("<td colspan=\"1\" rowspan=\"1\">&nbsp;</td>")) {
                    classLanght = Integer.parseInt(aClass.attr("rowspan"));
                    for (int k = 0; k < classLanght; k++) {
                        haveClass[j - 1][i + k - 1] = true;
                    }
                    tempText = aClass.toString();
                    if (!aClass.getElementsByTag("hr").isEmpty()) {
                        String[] temps = tempText.split("<hr>");
                        for (int k = 0; k < temps.length; k++) {
                            if (k != 0) {
                                setClass("&nbsp;" + temps[k], i, j, classLanght);
                            } else {
                                setClass(temps[k], i, j, classLanght);
                            }
                        }
                    } else {
                        setClass(tempText, i, j, classLanght);
                    }
                }
            }
        }
        databaseManager.closeDB();
    }

    private void setClass(String theClass, int i, int date, int classLanght) {
        ClassItem aClassItem;
        String[] allInfo = new String[3];
        String[] titleAndTimeAndDate = new String[4];
        String[] titleAndId = new String[2];
        String[] week = new String[20];
        String[] whichWeek = new String[2];

        theClass = theClass.replace("\n", "");
        allInfo = theClass.split("<br>", 3);
        titleAndTimeAndDate = allInfo[0].split("&nbsp;", 4);
        titleAndId = titleAndTimeAndDate[1].split("\\[", 2);
        titleAndId[1] = titleAndId[1].replaceAll("\\] ", "");
        week = titleAndTimeAndDate[2].split(",");
        for (int k = 0; k < week.length; k++) {
            whichWeek = week[k].split("周", 2);

            aClassItem = new ClassItem();

            aClassItem.setClassTitle(titleAndId[0]);
            aClassItem.setTeacher(allInfo[1]);
            aClassItem.setClassLocation(allInfo[2].replace(" </td>", ""));
            aClassItem.setClassNumber(Integer.parseInt(titleAndId[1]));
            aClassItem.setTimeStart(i);
            aClassItem.setTimeEnd(i + classLanght - 1);
            aClassItem.setClassTime(CLASS_START_TIME[i - 1] + "-" + CLASS_END_TIME[i + classLanght - 2]);
            aClassItem.setWeekStart(Integer.parseInt(whichWeek[0].split("-", 2)[0]));
            aClassItem.setWeekEnd(Integer.parseInt(whichWeek[0].split("-", 2)[1]));
            aClassItem.setDate(date);
            Log.d("which week", "\"" + whichWeek[1].replace("\\s", "").replace("\\s\\s", "").replace(" ", "") + "\"");
            if (whichWeek[1].replaceAll("\\s", "").replace("\\s\\s", "").replace(" ", "").equals("(单)")) {
                aClassItem.setSingleWeek(1);
                aClassItem.setDoubleWeek(0);
            } else if (whichWeek[1].replaceAll("\\s", "").replace("\\s\\s", "").replace(" ", "").equals("(双)")) {
                aClassItem.setSingleWeek(0);
                aClassItem.setDoubleWeek(1);
            } else {
                aClassItem.setSingleWeek(1);
                aClassItem.setDoubleWeek(1);
            }

            boolean found = false;
            for (int j = 0; j < classTitles.size(); j++) {
                if (aClassItem.getClassTitle().equals(classTitles.get(j))) {
                    found = true;
                    aClassItem.setColorID(classColors.get(j));
                }
            }
            if (!found) {
                aClassItem.setColorID(classCount);
                classColors.add(classCount);
                classCount++;
                classTitles.add(aClassItem.getClassTitle());
            }
            Log.d("got class", aClassItem.toString());
            databaseManager.addClass(aClassItem);
        }
    }

    private class ClassesCurrentNumberUpdater implements updateListener{
        @Override
        public void update() {
            updateCurrentNumber();
        }
    }

}