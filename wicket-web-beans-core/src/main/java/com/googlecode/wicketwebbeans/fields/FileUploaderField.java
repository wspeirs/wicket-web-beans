/*---
   Copyright 2006-2008 Visual Systems Corporation.
   http://www.vscorp.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
---*/

package com.googlecode.wicketwebbeans.fields;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.InlineFrame;
import org.apache.wicket.model.IModel;

import com.googlecode.wicketwebbeans.model.ElementMetaData;

/**
 * Ajax-based file upload field. Based on Vincent Demay's blog for an Ajax-based file upload panel (http://www.demay-fr.net/blog/index.php/2007/12/07/93-simulate-ajax-file-upload-with-wicket).<p>
 * 
 * @author Dan Syrstad
 */
public class FileUploaderField extends AbstractField
{
    private static final long serialVersionUID = 7582606175984978701L;

    private InlineFrame uploadIFrame = null;

    public FileUploaderField(String id, IModel model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly);

        addOnUploadedCallback();
        setOutputMarkupId(true);
    }

    /**
     * Called when the upload load is uploaded and ready to be used
     * Return the url of the new uploaded resource
     * @param upload {@link FileUpload}
     * @return
     */
    public String onFileUploaded(FileUpload upload)
    {
        if (upload == null) {
            return null;
        }

        // Try to use same file extension as client
        String clientFileName = upload.getClientFileName();
        int idx = clientFileName.lastIndexOf('.');
        String suffix = "";
        if (idx > 0 && clientFileName.length() > (idx + 1)) {
            suffix = clientFileName.substring(idx);
        }

        try {
            return File.createTempFile("FileUploaderField", suffix).getAbsolutePath();
        }
        catch (IOException e) {
            throw new WicketRuntimeException("Cannot create temp file", e);
        }
    }

    /**
     * Called once the upload is finished and the treatment of the
     * {@link FileUpload} has been done in {@link #onFileUploaded}.
     * 
     * @param target an {@link AjaxRequestTarget}
     * @param filename
     * @param newFileUrl Url of the uploaded file
     */
    public void onUploadFinished(AjaxRequestTarget target, String filename, String newFileUrl)
    {
        setDefaultModelObject(newFileUrl);
    }

    protected void onBeforeRender()
    {
        super.onBeforeRender();
        if (uploadIFrame == null) {
            // the iframe should be attached to a page to be able to get its pagemap,
            // that's why i'm adding it in onBeforRender
            addUploadIFrame();
        }
    }

    /**
     * Create the iframe containing the upload widget
     *
     */
    private void addUploadIFrame()
    {
        IPageLink iFrameLink = new IPageLink() {
            private static final long serialVersionUID = 1L;

            public Page getPage()
            {
                return new FileUploaderIFrame() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    protected String getOnUploadedCallback()
                    {
                        return "onUpload_" + FileUploaderField.this.getMarkupId();
                    }

                    @Override
                    protected String manageInputSream(FileUpload upload)
                    {
                        return FileUploaderField.this.onFileUploaded(upload);
                    }
                };
            }

            public Class<? extends Page> getPageIdentity()
            {
                return FileUploaderIFrame.class;
            }
        };
        uploadIFrame = new InlineFrame("upload", iFrameLink);
        add(uploadIFrame);
    }

    /**
     * Hackie method allowing to add a javascript in the page defining the
     * callback called by the innerIframe
     *
     */
    private void addOnUploadedCallback()
    {
        final OnUploadedBehavior onUploadBehavior = new OnUploadedBehavior();
        add(onUploadBehavior);
        add(new WebComponent("onUploaded") {
            private static final long serialVersionUID = 1L;

            public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
            {
                // calling it through setTimeout we ensure that the callback is called
                // in the proper execution context, that is the parent frame
                replaceComponentTagBody(markupStream, openTag, "function onUpload_"
                        + FileUploaderField.this.getMarkupId()
                        + "(clientFileName, newFileUrl) {window.setTimeout(function() { "
                        + onUploadBehavior.getCallback() + " }, 0 )}");
            }
        });
    }


    private class OnUploadedBehavior extends AbstractDefaultAjaxBehavior
    {
        private static final long serialVersionUID = 1L;

        public String getCallback()
        {
            return generateCallbackScript(
                    "wicketAjaxGet('" + getCallbackUrl()
                    + "&newFileUrl=' + encodeURIComponent(newFileUrl)"
                    + " + '&clientFileName=' + encodeURIComponent(clientFileName)").toString();
        }

        protected void respond(AjaxRequestTarget target)
        {
            FileUploaderField.this.onUploadFinished(target,
                    getRequest().getRequestParameters().getParameterValue("clientFileName").toString(),
                    getRequest().getRequestParameters().getParameterValue("newFileUrl").toString());
        }
    };
}
