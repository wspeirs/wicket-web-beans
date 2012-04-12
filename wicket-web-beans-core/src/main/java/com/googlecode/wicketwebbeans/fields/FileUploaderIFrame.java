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

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

/**
 * Support for FileUploaderField. Based on Vincent Demay's blog for an Ajax-based file upload panel (http://www.demay-fr.net/blog/index.php/2007/12/07/93-simulate-ajax-file-upload-with-wicket).<p>
 * 
 * @author Dan Syrstad
 */
public abstract class FileUploaderIFrame extends WebPage
{

    private boolean uploaded = false;
    private FileUploadField uploadField;
    private String newFileUrl;
    private Model<String> clientFileName = new Model<String>("");

    public FileUploaderIFrame()
    {
        add(new UploadForm("form"));
        addOnUploadedCallback();
        
    }

    /**
     * return the callback url when upload is finished
     * @return callback url when upload is finished
     */
    protected abstract String getOnUploadedCallback();

    /**
     * Called when the input stream has been uploaded and when it is available 
     * on server side
     * return the url of the uploaded file
     * @param upload fileUpload
     * @return
     */
    protected abstract String manageInputSream(FileUpload upload);


    private final class UploadForm extends Form
    {
        private static final long serialVersionUID = 1L;

        public UploadForm(String id)
        {
            super(id);
            uploadField = new FileUploadField("file", new Model("/etc/termcap"));
            AjaxEventBehavior onChangeEventHandler = new AjaxEventBehavior("onchange") {
                private static final long serialVersionUID = 1L;
                @Override
                public void onEvent(AjaxRequestTarget target)
                {
                    target.appendJavascript("showFileUploaderFieldIndicator()");
                }
            };
            
            onChangeEventHandler.setThrottleDelay(Duration.valueOf(1000L));
            uploadField.add(onChangeEventHandler);
            add(uploadField);
            
            add(new Image("indicator", "indicator.gif"));
            add(new Label("clientFileName", clientFileName));
        }

        public void onSubmit()
        {
            FileUpload upload = uploadField.getFileUpload();
            if (upload == null) {
                clientFileName.setObject("Invalid file");
            }
            else {
                clientFileName.setObject(upload.getClientFileName() + " (" + upload.getSize() + " bytes, type " + upload.getContentType() + ")");
            }

            newFileUrl = manageInputSream(upload);
            // File is now uploaded, and the IFrame will be reloaded, during
            // reload we need to run the callback
            uploaded = true;
        }

    }

    private void addOnUploadedCallback()
    {
        // Run the callback on the parent
        add(new WebComponent("onUploaded") {
            private static final long serialVersionUID = 1L;
            protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
            {
                if (uploaded) {
                    if (uploadField.getFileUpload() != null) {
                        replaceComponentTagBody(markupStream, openTag, "window.parent." + getOnUploadedCallback()
                                        + "('" + uploadField.getFileUpload().getClientFileName() + "','" + newFileUrl
                                        + "')");
                    }
                    uploaded = false;
                }
            }
        });
    }
}
