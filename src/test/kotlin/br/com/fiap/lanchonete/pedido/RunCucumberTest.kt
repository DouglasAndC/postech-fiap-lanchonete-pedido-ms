package br.com.fiap.pedido

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/test/resources/features/pedido.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value = "br.com.fiap.lanchonete.pedido.steps")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,value = "pretty, html:target/cucumber-report/cucumber.html")
class RunCucumberTest  {
}