package com.boothj5.jarch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import com.boothj5.jarch.analyser.Analyser;
import com.boothj5.jarch.analyser.RuleSetResult;
import com.boothj5.jarch.analyser.Violation;
import com.boothj5.jarch.analyser.ViolationType;
import com.boothj5.jarch.configuration.JArchConfig;
import com.boothj5.jarch.configuration.JArchConfigReader;

public class InvalidTest {
    private final String srcPath = "test" + File.separator + "resources" + File.separator + "invalid";
    private final String configFile = "test" + File.separator + "resources" + File.separator 
            + "jarch-config.xml";
    
    private String absSrcPath;
    private JArchConfig conf;
    private Analyser analyser;
    private List<RuleSetResult> results;
    
    @Before
    public void setUp() throws IOException, JDOMException {
        File srcDir = new File(srcPath);
        absSrcPath = srcDir.getAbsolutePath();
        conf = JArchConfigReader.parse(configFile);
        analyser = new Analyser(absSrcPath, conf.getLayerSpecs(), conf.getRuleSets());
        results = analyser.analyse();
    }
    
    @Test
    public void analyserReturnsCorrectModuleErrorCount() throws IOException {
        assertEquals(15, analyser.getNumModuleErrors());
    }
    
    @Test
    public void analyserReturnsCorrectLayerErrorCount() throws IOException {
        assertEquals(8, analyser.getNumLayerErrors());
    }

    @Test 
    public void analyserReturnsCorrectNumberOfRuleSetResults() {
        assertEquals(2, results.size());
    }
    
    @Test 
    public void analyserReturnsApplicationModuleDependenciesViolations() {
        boolean found = false;
        
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("application-module-dependencies")) {
                found = true;
            }
        }
        
        assertTrue(found);
    }

    @Test 
    public void analyserReturnsProjectDependenciesViolations() {
        boolean found = false;
        
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("project-dependencies")) {
                found = true;
            }
        }
        
        assertTrue(found);
    }

    @Test 
    public void analyserReturnsCorrectNumberOfApplicationModuleDependenciesViolations() {
        int numViolations = 0;
        
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("application-module-dependencies")) {
                numViolations = result.getViolations().size();
            }
        }
        
        assertEquals(8, numViolations);
    }

    @Test 
    public void analyserReturnsCorrectNumberOfProjectDependenciesViolations() {
        int numViolations = 0;
        
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("project-dependencies")) {
                numViolations = result.getViolations().size();
            }
        }
        
        assertEquals(15, numViolations);
    }

    @Test 
    public void analyserReturnsCorrectApplicationModuleDependenciesViolations() {
        Violation violation;
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("application-module-dependencies")) {
                
                violation = new Violation("MODULE: 'common' must not import from 'person'", 
                        "com.boothj5.jarchexample.application.common.StringUtil", 
                        5, 
                        "import com.boothj5.jarchexample.application.person.service.PersonService;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));
            
                violation = new Violation("MODULE: 'address' must not import from 'person'",
                        "com.boothj5.jarchexample.application.address.facade.AddressFacade",
                        7,
                        "import com.boothj5.jarchexample.application.person.service.PersonService;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'address' must not import from 'telephonenumber'",
                        "com.boothj5.jarchexample.application.address.facade.AddressFacade",
                        8,
                        "import com.boothj5.jarchexample.application.telephonenumber.service.TelephoneNumberService;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'service' in " +
                		"module 'telephonenumber' according to layer-spec 'spring'",
                		"com.boothj5.jarchexample.application.telephonenumber.controller.TelephoneNumberController",
                		7,
                		"import com.boothj5.jarchexample.application.telephonenumber.service.TelephoneNumberService;",
                		ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'dao' must not import from 'controller' in " +
                		"module 'telephonenumber' according to layer-spec 'spring'",
                		"com.boothj5.jarchexample.application.telephonenumber.dao.TelephoneNumberDAO",
                		6,
                		"import com.boothj5.jarchexample.application.telephonenumber.controller." +
                		"TelephoneNumberController;",
                		ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'service' in " +
                		"module 'person' according to layer-spec 'spring'",
                		"com.boothj5.jarchexample.application.person.controller.PersonController",
                		7,
                		"import com.boothj5.jarchexample.application.person.service.PersonService;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'repository' in " +
                		"module 'person' according to layer-spec 'spring'",
                		"com.boothj5.jarchexample.application.person.controller.PersonController",
                		8,
                		"import com.boothj5.jarchexample.application.person.repository.PersonRepository;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'dao' in " +
                		"module 'person' according to layer-spec 'spring'",
                		"com.boothj5.jarchexample.application.person.controller.PersonController",
                		9,
                		"import com.boothj5.jarchexample.application.person.dao.PersonDAO;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

            }
        }
    }

    @Test 
    public void analyserReturnsCorrectProjectDependenciesViolations() {
        Violation violation;
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("project-dependencies")) {

                violation = new Violation("MODULE: 'common' must not import from 'application'",
                        "com.boothj5.jarchexample.common.DateUtil",
                        5,
                        "import com.boothj5.jarchexample.application.common.StringUtil;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'common' must not import from 'domain'",
                        "com.boothj5.jarchexample.common.DateUtil",
                        6,
                        "import com.boothj5.jarchexample.domain.Person;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'common' must not import from 'dto'",
                        "com.boothj5.jarchexample.common.DateUtil",
                        7,
                        "import com.boothj5.jarchexample.dto.PersonDTO;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'common' must not import from 'configuration'",
                        "com.boothj5.jarchexample.common.DateUtil",
                        8,
                        "import com.boothj5.jarchexample.configuration.controller.ConfigurationController;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'dto' must not import from 'application'",
                        "com.boothj5.jarchexample.dto.PersonDTO",
                        4,
                        "import com.boothj5.jarchexample.application.common.StringUtil;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'dto' must not import from 'domain'",
                        "com.boothj5.jarchexample.dto.PersonDTO",
                        5,
                        "import com.boothj5.jarchexample.domain.Person;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'dto' must not import from 'configuration'",
                        "com.boothj5.jarchexample.dto.PersonDTO",
                        7,
                        "import com.boothj5.jarchexample.configuration.controller.ConfigurationController;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));
                
                violation = new Violation("LAYER: 'controller' must not import from 'service' in module 'configuration' according to layer-spec 'spring'",
                        "com.boothj5.jarchexample.configuration.controller.ConfigurationController",
                        7,
                        "import com.boothj5.jarchexample.configuration.service.ConfigurationService;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'repository' in module 'configuration' according to layer-spec 'spring'",
                        "com.boothj5.jarchexample.configuration.controller.ConfigurationController",
                        8,
                        "import com.boothj5.jarchexample.configuration.repository.ConfigurationRepository;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("LAYER: 'controller' must not import from 'dao' in module 'configuration' according to layer-spec 'spring'",
                        "com.boothj5.jarchexample.configuration.controller.ConfigurationController",
                        9,
                        "import com.boothj5.jarchexample.configuration.dao.ConfigurationDAO;",
                        ViolationType.LAYER);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'configuration' must not import from 'application'",
                        "com.boothj5.jarchexample.configuration.service.ConfigurationService",
                        7,
                        "import com.boothj5.jarchexample.application.common.StringUtil;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'configuration' must not import from 'domain'",
                        "com.boothj5.jarchexample.configuration.service.ConfigurationService",
                        8,
                        "import com.boothj5.jarchexample.domain.Person;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'configuration' must not import from 'dto'",
                        "com.boothj5.jarchexample.configuration.service.ConfigurationService",
                        9,
                        "import com.boothj5.jarchexample.dto.PersonDTO;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'domain' must not import from 'application'",
                        "com.boothj5.jarchexample.domain.Person",
                        6,
                        "import com.boothj5.jarchexample.application.common.StringUtil;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));

                violation = new Violation("MODULE: 'domain' must not import from 'configuration'",
                        "com.boothj5.jarchexample.domain.Person",
                        7,
                        "import com.boothj5.jarchexample.configuration.controller.ConfigurationController;",
                        ViolationType.MODULE);
                assertTrue(result.getViolations().contains(violation));
            }
        }
    }
}
