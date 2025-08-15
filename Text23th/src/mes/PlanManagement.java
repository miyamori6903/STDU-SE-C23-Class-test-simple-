package mes;

/**
 * 班级：[请填写班级]
 * 学号：[请填写学号]
 * 姓名：[请填写姓名]
 */
import java.util.Scanner;

public class PlanManagement {
    // 1. 初始化数组存储日报记录（至少5个，这里定义长度为10备用）
    private PlanInformation[] planArray;
    private int currentCount; // 已存储的计划数量
    private Scanner scanner; // 用于接收用户输入

    // 构造方法：初始化数组和输入工具
    public PlanManagement() {
        planArray = new PlanInformation[10]; // 数组长度10，满足“至少5个”要求
        currentCount = 0;
        scanner = new Scanner(System.in);
    }

    // 2. 主菜单方法（修复错误1：实现showMainMenu()）
    public void showMainMenu() {
        while (true) {
            // 打印主界面
            System.out.println("***********************************************************");
            System.out.println("石家庄铁道大学无限23软件开发有限公司");
            System.out.println("Mes系统2023版");
            System.out.println("***********************************************************");
            System.out.println("1、生成计划");
            System.out.println("2、提交日报");
            System.out.println("3、确认日报");
            System.out.println("4、统计进度");
            System.out.println("**********************************************************");
            System.out.print("请输入功能编号（1-4）：");

            // 接收用户输入并处理
            String input = scanner.next();
            switch (input) {
                case "1":
                    generatePlan(); // 生成计划
                    break;
                case "2":
                    submitDailyReport(); // 提交日报
                    break;
                case "3":
                    confirmDailyReport(); // 确认日报
                    break;
                case "4":
                    statProgress(); // 统计进度
                    break;
                default:
                    System.out.println("该选项不存在，请重新输入！");
            }
        }
    }

    // 3. 生成计划功能（按试卷要求实现）
    private void generatePlan() {
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");

        // 录入产品批次（验证格式：8位，含“-”，如2409-018）
        String planId = "";
        while (true) {
            System.out.print("请输入产品批次（8位，格式如2409-018）：");
            planId = scanner.next();
            // 正则验证：前4位数字 + “-” + 后3位数字
            if (planId.matches("\\d{4}-\\d{3}")) {
                break;
            } else {
                System.out.println("录入错误！产品批次格式应为8位（如2409-018），请重新录入！");
            }
        }

        // 录入产品名称（非空即可）
        System.out.print("请输入产品名称：");
        String planName = scanner.next();

        // 录入计划数量（正整数）
        int planNumber = 0;
        while (true) {
            try {
                System.out.print("请输入计划数量（正整数）：");
                planNumber = scanner.nextInt();
                if (planNumber > 0) {
                    break;
                } else {
                    System.out.println("录入错误！计划数量需为正整数，请重新录入！");
                }
            } catch (Exception e) {
                System.out.println("录入错误！请输入数字！");
                scanner.next(); // 清空错误输入
            }
        }

        // 显示录入信息，确认是否提交
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");
        System.out.println("产品批次：" + planId);
        System.out.println("产品名称：" + planName);
        System.out.println("计划数量：" + planNumber);
        System.out.println("该产品入库操作已完成，是否提交（Y/N）");
        System.out.println("**********************************************************");

        String confirm = scanner.next().toUpperCase();
        if ("Y".equals(confirm)) {
            // 生成日报记录（id自增，状态statement=0）
            PlanInformation newPlan = new PlanInformation(
                currentCount + 1, // id依次加1
                planId,
                planName,
                "", "", "", "", // 初始计划暂无工序信息
                planNumber,
                0, 0, 0, 0, 0, // 初始数量均为0
                0 // statement=0（初始计划）
            );
            // 添加到数组（修复错误2：数组存储逻辑）
            if (currentCount < planArray.length) {
                planArray[currentCount] = newPlan;
                currentCount++;
                System.out.println("提交成功！返回主界面！");
            } else {
                System.out.println("存储已满，无法添加新计划！");
            }
        } else {
            System.out.println("已忽略录入内容，返回产品入库界面！");
            generatePlan(); // 重新进入生成计划界面
        }
    }

    // 4. 提交日报功能（核心逻辑，按试卷要求实现）
    private void submitDailyReport() {
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");
        System.out.print("请输入产品批次号：");
        String planId = scanner.next();

        // 查询该批次是否存在（修复错误2：数组查询逻辑）
        PlanInformation plan = findPlanByPlanId(planId);
        if (plan == null) {
            System.out.println("库中没有该产品计划，返回提交界面！");
            submitDailyReport();
            return;
        }

        // 处理不同状态（statement=0或2）
        if (plan.getStatement() == 0) {
            // 状态0：显示基础信息，录入工序和数量
            System.out.println("***********************************************************");
            System.out.println("石家庄铁道大学无限23软件开发有限公司");
            System.out.println("Mes系统2023版");
            System.out.println("***********************************************************");
            System.out.println("1、产品批次：" + plan.getPlanid());
            System.out.println("2、产品名称：" + plan.getPlanname());
            System.out.println("3、计划数量：" + plan.getPlannumber());

            // 录入当前工序（如10.00.射蜡）
            System.out.print("4、当前工序：");
            String process = scanner.next();
            // 录入下一工序
            System.out.print("5、下一工序：");
            String nextProcess = scanner.next();
            // 自动生成操作员/接收员（按工艺表）
            String operator = getOperatorByProcess(process);
            String recipient = getRecipientByProcess(nextProcess);
            System.out.println("6、操作员：" + operator);
            System.out.println("7、接收员：" + recipient);

            // 录入上一道工序转入数量（第一道工序无需录入，默认=计划数量）
            int innumber = 0;
            if (isFirstProcess(process)) {
                innumber = plan.getPlannumber();
                System.out.println("8、上一道工序转入数量：" + innumber + "（第一道工序无需录入）");
            } else {
                while (true) {
                    try {
                        System.out.print("8、上一道工序转入数量（≤计划数量）：");
                        innumber = scanner.nextInt();
                        if (innumber <= plan.getPlannumber() && innumber >= 0) {
                            break;
                        } else {
                            System.out.println("录入错误！转入数量需≤计划数量且≥0！");
                        }
                    } catch (Exception e) {
                        System.out.println("录入错误！请输入数字！");
                        scanner.next();
                    }
                }
            }

            // 录入转出总数、丢失数量、废品数量、待检验数量（需满足押平规则）
            int outnumber = 0, missnumber = 0, badnumber = 0, inspectednumber = 0;
            while (true) {
                try {
                    System.out.print("9、转出总数（≤转入数量）：");
                    outnumber = scanner.nextInt();
                    System.out.print("10、丢失数量：");
                    missnumber = scanner.nextInt();
                    System.out.print("11、废品数量：");
                    badnumber = scanner.nextInt();
                    System.out.print("12、待检验数量：");
                    inspectednumber = scanner.nextInt();

                    // 验证押平规则：转入数量 = 转出总数 + 丢失 + 废品 + 待检验
                    if (innumber == outnumber + missnumber + badnumber + inspectednumber 
                        && outnumber <= innumber && outnumber >= 0 
                        && missnumber >= 0 && badnumber >= 0 && inspectednumber >= 0) {
                        break;
                    } else {
                        System.out.println("录入错误！需满足：转入数量=转出总数+丢失数量+废品数量+待检验数量，且所有数量≥0！");
                    }
                } catch (Exception e) {
                    System.out.println("录入错误！请输入数字！");
                    scanner.next();
                }
            }

            // 确认保存
            System.out.print("是否保存提交（Y/N）：");
            String confirm = scanner.next().toUpperCase();
            if ("Y".equals(confirm)) {
                // 更新计划信息，状态设为1（已提交）
                plan.setProcess(process);
                plan.setNextprocess(nextProcess);
                plan.setOperator(operator);
                plan.setRecipient(recipient);
                plan.setInnumber(innumber);
                plan.setOutnumber(outnumber);
                plan.setMissnumber(missnumber);
                plan.setBadnumber(badnumber);
                plan.setInspectednumber(inspectednumber);
                plan.setStatement(1); // 状态更新为1
                System.out.println("提交成功！返回主界面！");
            } else {
                System.out.println("不保存，返回主界面！");
            }
        } else if (plan.getStatement() == 2) {
            // 状态2：显示已有工序信息，录入剩余数量（逻辑类似，简化实现）
            System.out.println("***********************************************************");
            System.out.println("石家庄铁道大学无限23软件开发有限公司");
            System.out.println("Mes系统2023版");
            System.out.println("***********************************************************");
            System.out.println("1、产品批次：" + plan.getPlanid());
            System.out.println("2、产品名称：" + plan.getPlanname());
            System.out.println("3、计划数量：" + plan.getPlannumber());
            System.out.println("4、当前工序：" + plan.getProcess());
            System.out.println("5、下一工序：" + plan.getNextprocess());
            System.out.println("6、操作员：" + plan.getOperator());
            System.out.println("7、接收员：" + plan.getRecipient());
            System.out.println("8、上一道工序转入数量：" + plan.getInnumber());

            // 录入剩余数量（同上，需满足押平规则）
            int outnumber = 0, missnumber = 0, badnumber = 0, inspectednumber = 0;
            while (true) {
                try {
                    System.out.print("9、转出总数（≤转入数量）：");
                    outnumber = scanner.nextInt();
                    System.out.print("10、丢失数量：");
                    missnumber = scanner.nextInt();
                    System.out.print("11、废品数量：");
                    badnumber = scanner.nextInt();
                    System.out.print("12、待检验数量：");
                    inspectednumber = scanner.nextInt();

                    if (plan.getInnumber() == outnumber + missnumber + badnumber + inspectednumber 
                        && outnumber <= plan.getInnumber() && outnumber >= 0) {
                        break;
                    } else {
                        System.out.println("录入错误！请满足押平规则且数量合法！");
                    }
                } catch (Exception e) {
                    System.out.println("录入错误！请输入数字！");
                    scanner.next();
                }
            }

            // 确认保存
            System.out.print("是否保存提交（Y/N）：");
            String confirm = scanner.next().toUpperCase();
            if ("Y".equals(confirm)) {
                plan.setOutnumber(outnumber);
                plan.setMissnumber(missnumber);
                plan.setBadnumber(badnumber);
                plan.setInspectednumber(inspectednumber);
                plan.setStatement(1);
                System.out.println("提交成功！返回主界面！");
            } else {
                System.out.println("不保存，返回主界面！");
            }
        } else {
            System.out.println("该批次状态异常（当前状态：" + plan.getStatement() + "），返回主界面！");
        }
    }

    // 5. 确认日报功能（按试卷要求实现）
    private void confirmDailyReport() {
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");
        System.out.print("请输入产品批次：");
        String planId = scanner.next();

        // 查询计划
        PlanInformation plan = findPlanByPlanId(planId);
        if (plan == null) {
            System.out.println("库中没有该产品批次，返回确认日报界面！");
            confirmDailyReport();
            return;
        }

        // 仅处理状态为1（已提交）的计划
        if (plan.getStatement() == 1) {
            // 显示当前信息
            System.out.println("***********************************************************");
            System.out.println("石家庄铁道大学无限23软件开发有限公司");
            System.out.println("Mes系统2023版");
            System.out.println("***********************************************************");
            System.out.println("1、产品批次：" + plan.getPlanid());
            System.out.println("2、产品名称：" + plan.getPlanname());
            System.out.println("3、计划数量：" + plan.getPlannumber());
            System.out.println("4、当前工序：" + plan.getProcess());
            System.out.println("5、下一工序：" + plan.getNextprocess());
            System.out.println("6、操作员：" + plan.getOperator());
            System.out.println("7、接收员：" + plan.getRecipient());
            System.out.println("8、上一道工序转入数量：" + plan.getInnumber());
            System.out.println("9、转出总数：" + plan.getOutnumber());
            System.out.println("10、丢失数量：" + plan.getMissnumber());
            System.out.println("11、废品数量：" + plan.getBadnumber());
            System.out.println("12、待检验数量：" + plan.getInspectednumber());
            System.out.println("**********************************************************");

            System.out.print("是否确认（Y/N）：");
            String confirm = scanner.next().toUpperCase();
            if ("Y".equals(confirm)) {
                // 计算新的转入数量：转出总数 - 丢失 - 废品 - 待检验
                int newInnumber = plan.getOutnumber() - plan.getMissnumber() - plan.getBadnumber() - plan.getInspectednumber();
                // 自动获取下一工序（按工艺表递进，简化实现）
                String newProcess = plan.getNextprocess();
                String newNextProcess = getNextProcessByCurrent(newProcess);
                String newOperator = getOperatorByProcess(newProcess);
                String newRecipient = getRecipientByProcess(newNextProcess);

                // 更新当前计划状态为2（已确认）
                plan.setStatement(2);
                // 生成新的任务记录（若数组未满）
                if (currentCount < planArray.length) {
                    PlanInformation newPlan = new PlanInformation(
                        currentCount + 1,
                        planId,
                        plan.getPlanname(),
                        newProcess,
                        newNextProcess,
                        newOperator,
                        newRecipient,
                        plan.getPlannumber(),
                        newInnumber,
                        0, 0, 0, 0,
                        0 // 新任务状态为0
                    );
                    planArray[currentCount] = newPlan;
                    currentCount++;
                }

                // 显示确认后的信息
                System.out.println("***********************************************************");
                System.out.println("石家庄铁道大学无限23软件开发有限公司");
                System.out.println("Mes系统2023版");
                System.out.println("***********************************************************");
                System.out.println("1、产品批次：" + planId);
                System.out.println("2、产品名称：" + plan.getPlanname());
                System.out.println("3、计划数量：" + plan.getPlannumber());
                System.out.println("4、当前工序：" + newProcess);
                System.out.println("5、下一工序：" + newNextProcess);
                System.out.println("6、操作员：" + newOperator);
                System.out.println("7、接收员：" + newRecipient);
                System.out.println("8、上一道工序转入数量：" + newInnumber);
                System.out.println("**********************************************************");
                System.out.println("确认成功！返回主界面！");
            } else {
                System.out.println("返回当前界面！");
                confirmDailyReport();
            }
        } else {
            System.out.println("该批次状态异常（当前状态：" + plan.getStatement() + "），仅状态1可确认！");
        }
    }

    // 6. 统计进度功能（按试卷要求实现）
    private void statProgress() {
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");
        System.out.print("请输入产品批次：");
        String planId = scanner.next();

        // 查询该批次的所有记录（找到最后一道工序）
        PlanInformation lastProcessPlan = null;
        for (int i = 0; i < currentCount; i++) {
            if (planId.equals(planArray[i].getPlanid())) {
                lastProcessPlan = planArray[i];
            }
        }

        if (lastProcessPlan == null) {
            System.out.println("无该产品批次信息！");
            return;
        }

        // 计算完成数量（最后一道工序的转出数量）
        int finishCount = lastProcessPlan.getOutnumber();
        int remainCount = lastProcessPlan.getPlannumber() - finishCount;

        // 显示统计结果
        System.out.println("***********************************************************");
        System.out.println("石家庄铁道大学无限23软件开发有限公司");
        System.out.println("Mes系统2023版");
        System.out.println("***********************************************************");
        System.out.println("1、产品批次：" + lastProcessPlan.getPlanid());
        System.out.println("2、产品名称：" + lastProcessPlan.getPlanname());
        System.out.println("3、计划数量：" + lastProcessPlan.getPlannumber());
        System.out.println("4、完成数量：" + finishCount);
        System.out.println("5、剩余数量：" + remainCount);
        System.out.println("**********************************************************");
    }

    // 辅助方法1：根据产品批次查询计划
    private PlanInformation findPlanByPlanId(String planId) {
        for (int i = 0; i < currentCount; i++) {
            if (planId.equals(planArray[i].getPlanid())) {
                return planArray[i];
            }
        }
        return null;
    }

    // 辅助方法2：根据工序号获取操作员（按试卷工艺表）
    private String getOperatorByProcess(String process) {
        // 提取工序号（如“10.00.射蜡”提取“10”）
        String processNum = process.split("\\.")[0];
        switch (processNum) {
            case "10": return "柳泽羽";
            case "20": return "藤艺哲";
            case "30": return "郝一诺";
            case "40": return "杨松铎";
            case "50": return "刘宇恒";
            case "60": return "陶攀印";
            case "70": return "阎琪文";
            case "80": return "郝正一";
            case "90": return "刘静一";
            default: return "未知操作员";
        }
    }

    // 辅助方法3：根据工序号获取接收员（下一工序的操作员）
    private String getRecipientByProcess(String nextProcess) {
        if (nextProcess == null || nextProcess.isEmpty()) {
            return "无";
        }
        return getOperatorByProcess(nextProcess);
    }

    // 辅助方法4：判断是否为第一道工序（工序号10）
    private boolean isFirstProcess(String process) {
        return process.startsWith("10");
    }

    // 辅助方法5：根据当前工序获取下一工序（按工艺表顺序）
    private String getNextProcessByCurrent(String currentProcess) {
        String processNum = currentProcess.split("\\.")[0];
        switch (processNum) {
            case "10": return "20.00.修蜡";
            case "20": return "30.00.面层";
            case "30": return "40.00.封浆";
            case "40": return "50.00.熔化";
            case "50": return "60.00.切割";
            case "60": return "70.00.精磨";
            case "70": return "80.00.调型";
            case "80": return "90.00.检验";
            case "90": return "无"; // 最后一道工序
            default: return "未知工序";
        }
    }
}