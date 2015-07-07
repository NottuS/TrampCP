package gov.pr.celepar.abi.quartz;

import gov.pr.celepar.abi.facade.OperacaoFacade;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class VerificacaoAgendaABIQuartz implements Job {

	private static Logger log = Logger.getLogger(VerificacaoAgendaABIQuartz.class);	
	
	private static final String GRUPO = "VerificacaoAgendaABI";
	private static final String PREFIXO_JOB = "jobVerificacaoAgendaABI";
	private static final String PREFIXO_TRIGGER = "triggerVerificacaoAgendaABI";
	

	public static void agendar() throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail jobDetail = new JobDetail(PREFIXO_JOB, GRUPO , VerificacaoAgendaABIQuartz.class);  	   
		CronTrigger trigger = new CronTrigger(
				PREFIXO_TRIGGER, //java.lang.String name,
				GRUPO , //Scheduler.DEFAULT_GROUP,
				"0 0 04 * * ?" //Executa todos os dias as 04:00 hrs
			); //cronExpression

		scheduler.scheduleJob(jobDetail, trigger);	
	}	

	/**
	* Executar Job agendado.
	*/
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			log.error("Inicializando o Processamento do Monitoramento da Agenda.");
			OperacaoFacade.monitorarAgendaQuartz(null);
			log.error("Fim do Processamento do Monitoramento da Agenda.");
		} catch (Exception e) {
			log.error(e);
		}
	}
}
