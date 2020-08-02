package com.kiwigrid.helm.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

/**
 * Mojo for generating a template.
 *
 * @author Simon Billingsley
 * @since 01.08.20
 */
@Mojo(name = "template", defaultPhase = LifecyclePhase.TEST)
public class TemplateMojo extends AbstractHelmMojo {

	@Parameter(property = "helm.template.skip", defaultValue = "false")
	private boolean skipTemplate;

	@Parameter(property = "valuesFile")
	private String valuesFile;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip || skipTemplate) {
			getLog().info("Skip template");
			return;
		}
		for (String inputDirectory : getChartDirectories(getChartDirectory(), false)) {
			getLog().info("\n\nGenerate template for chart " + inputDirectory + "...");

			callCli(getHelmExecuteablePath()
					+ " template"
					+ " " + inputDirectory
					+ " --generate-name"
				    + (StringUtils.isNotEmpty(valuesFile) ? " --values " + valuesFile : "")
					+ (StringUtils.isNotEmpty(getRegistryConfig()) ? " --registry-config=" + getRegistryConfig() : "")
					+ (StringUtils.isNotEmpty(getRepositoryCache()) ? " --repository-cache=" + getRepositoryCache() : "")
					+ (StringUtils.isNotEmpty(getRepositoryConfig()) ? " --repository-config=" + getRepositoryConfig() : ""),
					"There are test failures", true);
		}
	}

	public String getValuesFile() {
		return valuesFile;
	}

	public void setValuesFile(String action) {
		this.valuesFile = action;
	}
}
