package com.wanli.utils;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries;
import org.swtchart.ISeries.SeriesType;

/**
 * ֻ�е�����ʱ��ʾ��ͼ��
 * @author wanli
 *
 */
public class SingleAnswerChartTableUtil extends Dialog {

	protected Object result;
	protected Shell shell;
	private static double[] ySeries = new double[3];									// ����new double[3]����Ϊ����ֻ����ȷ������δ�ش�����
	private static double[] optionYSeries = new double[StaticVariable.options.size()];	// StaticVariable.options.size()��ʾ�ж��ٸ�ѡ��
	private String questionType;
	private String answer;

	private static final String[] cagetorySeries = { "��ȷ", "����", "δ�ش�" };
	private String[] optionXSeries = new String[StaticVariable.options.size()];
	
	public SingleAnswerChartTableUtil(Shell parent, String tableName) {
		super(parent, SWT.NONE);
		init();
	}
	
	public Object open() {
		createContents(); 
		shell.open();  
        shell.layout();  
        Display display = getParent().getDisplay();  
        while (!shell.isDisposed()) {  
            if (!display.readAndDispatch())  
                display.sleep();  
        }  
        return result;
	}
	
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        shell.setText("ͼ��");
        shell.setImage(SWTResourceManager.getImage("image/quesCount.png"));
        shell.setSize(800, 300);
        shell.setLayout(new FillLayout());
        createChart(shell);
	}
	
	protected Chart createChart(Composite parent) {
		// ����ѡ�
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		GridData gridTab = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gridTab);
		// �����һ��ѡ�
		final TabItem tatistics = new TabItem(tabFolder, SWT.NONE);
		tatistics.setText("��ȷ��ͳ��");
		{
			answerStatistics(tabFolder, tatistics);
		}
		// ����ڶ���ѡ�
		if (!questionType.equals("true_or_false")) {
			final TabItem details = new TabItem(tabFolder, SWT.NONE);
			details.setText("����");
			{
				answerStatisticsDetails(tabFolder, details);
			}			
		}
		// Ϊѡ���Ӽ�����
		tabFolder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (tabFolder.getSelectionIndex() == 1) {
					answerStatistics(tabFolder, tatistics);
				} else if (tabFolder.getSelectionIndex() == 2) {
					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		return null;
    }
	
	// ��ͳ��
	private void answerStatistics(TabFolder tabFolder, TabItem tatistics) {
		//Ϊ��ѡ�����һ�����
		Composite questionComp = new Composite(tabFolder, SWT.BORDER);
		tatistics.setControl(questionComp);
		//���ø����Ϊ����ʽ����
		questionComp.setLayout(new FillLayout());
		// ����һ��ͼ��
		Chart chart = new Chart(questionComp, SWT.NONE);
		chart.getTitle().setText("�ɼ���");
		GridData gridTab = new GridData(GridData.FILL_BOTH);
		chart.setLayoutData(gridTab);
		// ����yֵ
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).setCategorySeries(cagetorySeries);
//		chart.getAxisSet().getXAxis(0).getTick().setTickLabelAngle(45);
		
		// �������ͼ
		ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR,
				"�𰸣�" + answer);
		barSeries.setYSeries(ySeries);
		chart.getAxisSet().adjustRange();
	}
	
	// ��ͳ������
	private void answerStatisticsDetails(TabFolder tabFolder, TabItem details) {
		//Ϊ��ѡ�����һ�����
		Composite questionComp = new Composite(tabFolder, SWT.BORDER);
		details.setControl(questionComp);
		//���ø����Ϊ����ʽ����
		questionComp.setLayout(new FillLayout());
		// ����һ��ͼ��
		Chart chart = new Chart(questionComp, SWT.NONE);
		chart.getTitle().setText("�ɼ���");
		GridData gridTab = new GridData(GridData.FILL_BOTH);
		chart.setLayoutData(gridTab);
		// ����yֵ
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).setCategorySeries(optionXSeries);
//		chart.getAxisSet().getXAxis(0).getTick().setTickLabelAngle(45);
		
		// �������ͼ
		IBarSeries barSeries = null;
		barSeries = (IBarSeries) chart.getSeriesSet().createSeries(
				SeriesType.BAR, "ѡ�����");
		
		barSeries.setYSeries(optionYSeries);
		chart.getAxisSet().adjustRange();
	}
	
	// ��ʼ��
	protected void init() {
		if (StaticVariable.unResponse.size() > 0) {
			// ������ȷ�𰸣�����𰸣�δ�ش�����������
			int unResponse = StaticVariable.unResponse.get(0).intValue();
			int correct = StaticVariable.correct.get(0).intValue();
			int error = StaticVariable.error.get(0).intValue();
			unResponse = StaticVariable.users.size() - correct - error;
//			System.out.println("users:" + StaticVariable.users.size());
			StaticVariable.unResponse.set(0, new Integer(unResponse));
//			System.out.println("correct" + correct);
//			System.out.println("error" + error);
//			System.out.println("unResponse" + unResponse);
			// ��ʼ��ÿ������ͼ������
			ySeries[0] = StaticVariable.correct.get(0).intValue();
			ySeries[1] = StaticVariable.error.get(0).intValue();
			ySeries[2] = StaticVariable.unResponse.get(0).intValue();
			// ���𰸱��浽answer
			int index = StaticVariable.questionSelect.getSelectionIndex();
//			String question = StaticVariable.questionsMap.get(Integer.toString(index));
			String question = StaticVariable.questionsList.get(index - 1);
			String[] strs = question.split("#\\^");
			questionType = strs[0];
			answer = strs[2];
			// ͳ��ÿ��ѡ��ĸ���
			int i = 1;
			for (Map.Entry<String, Integer> option: StaticVariable.options.entrySet()) {
				optionYSeries[i - 1] = option.getValue();
				optionXSeries[i - 1] = option.getKey();
				i++;
			}
//			System.out.println(StaticVariable.options);
		}
	}
	
}
