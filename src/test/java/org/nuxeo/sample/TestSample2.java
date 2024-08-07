package org.nuxeo.sample;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.AbstractSession;
import org.nuxeo.ecm.core.api.CoreInstance;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.security.ACE;
import org.nuxeo.ecm.core.api.security.ACL;
import org.nuxeo.ecm.core.api.security.ACP;
import org.nuxeo.ecm.core.api.security.SecurityConstants;
import org.nuxeo.ecm.core.model.Document;
import org.nuxeo.ecm.core.model.Session;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("org.nuxeo.sample.sample-test.tests:OSGI-INF/doctype-contrib.xml")
public class TestSample2 {

    @Inject
    protected CoreSession session;

    String id;

    @Before
    public void setUp() {
        DocumentModel fil1 = session.createDocumentModel("/", "test", "File");
        fil1 = session.createDocument(fil1);
        fil1.addFacet("myFacet");
        session.save();
        id = fil1.getId();
    }

    @Test
    public void run() throws OperationException {
        Session documentSession = ((AbstractSession) session).getSession();
        Document doc = documentSession.getDocumentByUUID(id);
        String lifeCycle = doc.getLifeCycleState();
        assertEquals(lifeCycle, "project");

        DocumentModel doc2 = session.getDocument(new PathRef("/test"));
        String lifeCycle2 = doc2.getCurrentLifeCycleState();
        assertEquals(lifeCycle2, "project");
    }
}
