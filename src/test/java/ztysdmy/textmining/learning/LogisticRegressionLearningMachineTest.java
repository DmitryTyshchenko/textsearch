package ztysdmy.textmining.learning;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

import static ztysdmy.textmining.learning.LogisticRegressionLearningMachine.*;

public class LogisticRegressionLearningMachineTest {

	/**@Test
	public void shouldCalculateError() throws Exception {
		var target = new Target<Binomial>(Binomial.YES);
		var fact = new Fact<Binomial>("abc", target);
		var predictionResult = new PredictionResult<Binomial>(target, 0.7d);
		Assert.assertEquals(0.3, round(error(fact, predictionResult)), 0.d);
	}**/

	private double round(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(3, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Test
	public void shouldCollectMonomials() throws Exception {

		var learningMachine = new LogisticRegressionLearningMachine(factsRepository());
		LogisticRegression logisticRegression = learningMachine.createLogisticRegression();
		learningMachine.collectMonomials(logisticRegression);

		var monomial1 = logisticRegression.monomial(new Term("a"));
		// means that Monomial with Term 'a' exists
		Assert.assertTrue(monomial1.weight() != 0.d);

		var monomial2 = logisticRegression.monomial(new Term("notexists"));
		// means that Monomial with Term 'notexists' exists
		Assert.assertTrue(monomial2.weight() == 0.d);

	}

	@Test
	public void sanityTest() throws Exception {

		var learningMachine = new LogisticRegressionLearningMachine(factsRepository());

		var logisticRegression = learningMachine.build();
		System.out.println(logisticRegression.toString());
		var result = logisticRegression.predict(new Fact<Binomial>("a"));
		Assert.assertTrue(result.probability() > 0.5);
		
		var result2 = logisticRegression.predict(new Fact<Binomial>("b"));
		Assert.assertFalse(result2.probability() > 0.5d);

		var result3 = logisticRegression.predict(new Fact<Binomial>("c"));
		Assert.assertTrue(result3.probability() > 0.5d);

	}

	FactsRepository<Binomial> factsRepository() {

		FactsRepository<Binomial> result = new InMemoryFactsRepository<>();
		result.add(facts());
		return result;
	}

	Collection<Fact<Binomial>> facts() {

		var result = new ArrayList<Fact<Binomial>>();

		for (int i = 0; i < 4; ++i) {
			var trueFact = fact("a", Binomial.YES);
			result.add(trueFact);
		}
		
		var trueFact2 = fact("c", Binomial.YES);
		result.add(trueFact2);

		
		var falseFact = fact("b", Binomial.NO);
		result.add(falseFact);
		return result;
	}

	//from https://www.sports.ru/barcelona/news/page2/
	Collection<Fact<Binomial>> barcelonaFacts() {
		
		var result = new ArrayList<Fact<Binomial>>();
		
		var trueFact1 = fact("Главный тренер «Барселоны» Эрнесто Вальверде высказался в преддверии матча 8-го тура Ла Лиги с «Севильей»." + 
				 
				"«Месси и Суарес снова в хорошей форме. Они продемонстрировали это в матче с «Интером» (2:1)." + 
				 
				"В воскресенье я приму решение, начнут ли они матч с первых минут или выйдут на поле позже. Оба игрока в этом сезоне столкнулись с травмами, но чем больше они будут играть, тем лучше будет их форма." + 
				 
				"У нас все отлично. Мы на хорошем ходу, сейчас нам предстоит матч с сильной командой. Наша цель – продолжать двигаться вперед», – сказал Вальверде. ", Binomial.YES);
		
		result.add(trueFact1);
		
		var trueFact2 = fact("Главный тренер «Барселоны» Эрнесто Вальверде поделился ожиданиями от предстоящего матча 4-го тура Ла Лиги с «Валенсией»." + 
				"«Я полагаю, что игра [«Валенсии»] не изменится. «Валенсия» сменила тренера, но это было всего два дня назад, так что мало что изменилось. С предыдущим тренером команда добилась хороших результатов, поэтому в матче с нами будет использовать свою прежнюю схему." + 
				"Возвращение Месси? Казалось, что это был небольшой перерыв в восстановлении. Вскрылись небольшие последствия, поэтому мы решили не торопить события. Не знаю, вернется ли он во вторник или это будет позже." + 
				"Не буду отрицать, что Лео важен для нас. Он является ключевым игроком, особенно в матчах с командами, которые глубоко отходят назад. Мы надеемся, что он скоро вернется." + 
				"Я не думаю, что Ракитич сильно расстроен из-за непопадания в состав. У нас много игроков в центре поля. Для всех было бы новостью, если бы Бускетс, Артуро Видаль или де Йонг не играли. Реальность такова, что в центре поля у «Барсы» высокая конкуренция." + 
				"Луис Суарес? Вчера и сегодня он провел тренировку. Надеюсь, постепенно он вернется в состав." + 
				"Мы обсуждали Неймара до, во время и после трансферного окна. Пришло время перевернуть страницу», – сказал Вальверде. ", Binomial.YES);
		
		result.add(trueFact2);
		
		var falseFact = fact("Полузащитник Усман Дембеле сегодня тренировался с «Барселоной»." + 
				 
				"Француз вернулся к занятиям раньше предполагаемого срока. Однако он занимался в общей группе лишь частично." + 
				 
				"Дембеле пропустил около месяца из-за травмы двуглавой мышцы левого бедра.", Binomial.NO);
		
		result.add(falseFact);
		
		return result;
	}
	
	
	Collection<Fact<Binomial>> javaFacts() {
		var result = new ArrayList<Fact<Binomial>>();
		
		var falseFact1 = fact("public void startTimer()" + 
				"        TimerService timerService = sessionContext.getTimerService();  " + 
				"        Collection<Timer> timers = timerService.getTimers();  " + 
				"        for (Timer timer : timers) {  " + 
				"            if (EVENT_TIMER.equals(timer.getInfo())) {  " + 
				"                try  " + 
				"                {  " + 
				"                    if (log.isDebugEnabled())  " + 
				"                    {  " + 
				"                        log.debug(\"Canceling timer \" + timer.getInfo());  " + 
				"                    }  " + 
				"                    timer.cancel();  " + 
				"                }  " + 
				"                catch (Exception e)  " + 
				"                {  " + 
				"                    // seems to be a bug where sometimes  " + 
				"                    // NoSuchObjectLocalException is thrown  " + 
				"                    // but timer is successfully canceled anyway  " + 
				"                    log.warn(\"Ignoring caught exception: \"  " + 
				"                            + e.getMessage());  " + 
				"                }  " + 
				"            }  " + 
				"        }  " + 
				"        Timer timer = timerService.createTimer(TIMER_START_INITIAL_DELAY, 15000L, EVENT_TIMER);  " + 
				
				"        List<Event> events = null;  " + 
				"        try  " + 
				"        {  " + 
				"            events = getEvents();  " + 
				"            getEventDistributor().initialize(events);  " + 
				"        }  " + 
				"        catch (Exception e)  " + 
				"        {  " + 
				"            log.error(\"Exception occured while loading event \" + events, e);  " + 
				"        }  " + 
				"    }", Binomial.NO);
		
		
		var trueFact1 = fact ("public void publish(Event event)"+
	    "{"+
	        "Validate.notEmpty(event.getType(), \"Event type has to be provided.\");"+
	        "event.setTimeUpdated(new Date());"+
	        "saveEvent(event);"+
	        "getEventDistributor().publish(event);"+
	    "}", Binomial.YES);


		result.add(trueFact1);
		result.add(falseFact1);
		return result;
	}
	
	@Test
	public void javaTest() throws Exception {

		FactsRepository<Binomial> repository = new InMemoryFactsRepository<>();
		repository.add(javaFacts());
		
		var learningMachine = new LogisticRegressionLearningMachine(repository);

		var logisticRegression = learningMachine.build();
		System.out.println(logisticRegression.toString());
		var result = logisticRegression.predict(new Fact<Binomial>("Function<Fact<Binomial>, TermsVector> generateTermsFromFact = fact->TermsVectorBuilder.build(fact, this.params.getComplexity());"));
		
		System.out.println("Code is Good "+(result.probability()>0.5d));
		System.out.println("Probability that code is good "+result.probability());

	}
	
	//@Test
	public void barcelonaTest() throws Exception {
		
		FactsRepository<Binomial> repository = new InMemoryFactsRepository<>();
		repository.add(barcelonaFacts());
		
		var learningMachine = new LogisticRegressionLearningMachine(repository);

		var logisticRegression = learningMachine.build();
		System.out.println(logisticRegression.toString());
		var result = logisticRegression.predict(new Fact<Binomial>("Месси и Суарес снова в хорошей форме"));
		
		System.out.println("Barcelona is win "+(result.probability()>0.5d));
		System.out.println("Probability that Barcelona win is "+result.probability());
		
	}
	
	private Fact<Binomial> fact(String value, Binomial targetValue) {
		var fact = new Fact<Binomial>(value, new Target<Binomial>(targetValue));

		return fact;
	}
}
