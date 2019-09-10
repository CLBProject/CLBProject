package clb.business.schedulers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExternalRoutines {

	@Transactional
	public void fetchAnalyzerRegistries() {
		System.out.println("Called!");
	}

}
