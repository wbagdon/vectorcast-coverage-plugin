package com.vectorcast.plugins.vectorcastcoverage;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Result;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.io.IOException;

/**
 * Project view extension by VectorCAST plugin.
 * 
 * @author Kohsuke Kawaguchi
 */
public final class VectorCASTProjectAction implements Action {
    public final AbstractProject<?,?> project;

    public VectorCASTProjectAction(AbstractProject project) {
        this.project = project;
    }

    public String getIconFileName() {
        return "graph.gif";
    }

    public String getDisplayName() {
        return Messages.ProjectAction_DisplayName();
    }

    public String getUrlName() {
        return "vectorcastcoverage";
    }

    /**
     * Gets the most recent {@link VectorCASTBuildAction} object.
     * @return last build result
     */
    public VectorCASTBuildAction getLastResult() {
        for( AbstractBuild<?,?> b = project.getLastBuild(); b!=null; b=b.getPreviousBuild()) {
            if(b.getResult()== Result.FAILURE)
                continue;
            VectorCASTBuildAction r = b.getAction(VectorCASTBuildAction.class);
            if(r!=null)
                return r;
        }
        return null;
    }

    public void doGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
       if (getLastResult() != null)
          getLastResult().doGraph(req,rsp);
    }
}
