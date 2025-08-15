package mes;

/**
 * 班级：[请填写班级]
 * 学号：[请填写学号]
 * 姓名：[请填写姓名]
 */
public class PlanInformation {
    // 14个私有变量
    private int id;
    private String planid;
    private String planname;
    private String process;
    private String nextprocess;
    private String operator;
    private String recipient;
    private int plannumber;
    private int innumber;
    private int outnumber;
    private int missnumber;
    private int badnumber;
    private int inspectednumber;
    private int statement;

    // 赋初值方法（构造方法，14个参数）
    public PlanInformation(int id, String planid, String planname, String process, 
                          String nextprocess, String operator, String recipient, 
                          int plannumber, int innumber, int outnumber, 
                          int missnumber, int badnumber, int inspectednumber, int statement) {
        this.id = id;
        this.planid = planid;
        this.planname = planname;
        this.process = process;
        this.nextprocess = nextprocess;
        this.operator = operator;
        this.recipient = recipient;
        this.plannumber = plannumber;
        this.innumber = innumber;
        this.outnumber = outnumber;
        this.missnumber = missnumber;
        this.badnumber = badnumber;
        this.inspectednumber = inspectednumber;
        this.statement = statement;
    }

    // 所有变量的get()和set()方法
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlanid() { return planid; }
    public void setPlanid(String planid) { this.planid = planid; }

    public String getPlanname() { return planname; }
    public void setPlanname(String planname) { this.planname = planname; }

    public String getProcess() { return process; }
    public void setProcess(String process) { this.process = process; }

    public String getNextprocess() { return nextprocess; }
    public void setNextprocess(String nextprocess) { this.nextprocess = nextprocess; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public int getPlannumber() { return plannumber; }
    public void setPlannumber(int plannumber) { this.plannumber = plannumber; }

    public int getInnumber() { return innumber; }
    public void setInnumber(int innumber) { this.innumber = innumber; }

    public int getOutnumber() { return outnumber; }
    public void setOutnumber(int outnumber) { this.outnumber = outnumber; }

    public int getMissnumber() { return missnumber; }
    public void setMissnumber(int missnumber) { this.missnumber = missnumber; }

    public int getBadnumber() { return badnumber; }
    public void setBadnumber(int badnumber) { this.badnumber = badnumber; }

    public int getInspectednumber() { return inspectednumber; }
    public void setInspectednumber(int inspectednumber) { this.inspectednumber = inspectednumber; }

    public int getStatement() { return statement; }
    public void setStatement(int statement) { this.statement = statement; }
}