/*---
   Copyright 2006-2007 Visual Systems Corporation.
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
package com.googlecode.wicketwebbeans.containers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicketwebbeans.actions.BeanActionButton;
import com.googlecode.wicketwebbeans.fields.AbstractField;
import com.googlecode.wicketwebbeans.fields.Field;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.BeanPropertyModel;
import com.googlecode.wicketwebbeans.model.ElementMetaData;
import com.googlecode.wicketwebbeans.model.TabMetaData;


/**
 * Generic component for presenting a bean form. Supports the following parameters: <p>
 * <ul>
 * <li>label - the form's label.</li>
 * <li>rows - if the bean is a List, this is the number of rows to be displayed. Defaults to 10.</li>
 * <li>container - a container to use in place of the default BeanGridPanel or BeanTablePanel. This container must must be a Panel and
 *   implement a constructor of the form: <p>
 *   <code>public Constructor(String id, final Object bean, BeanMetaData beanMetaData, TabMetaData tabMetaData)</code>
 *   <p>
 *   where id = Wicket component ID<br>
 *   bean = the bean, or IModel containing the bean<br>
 *   beanMetaData = the BeanMetaData for bean<br>
 *   tabMetaData = the tab metadata
 *   </li>
 * </ul>
 * 
 * You can override default error message for required field.<br>
 * Property key of the message: <em>wicketwebbeans.BeanForm.fieldIsRequired</em><br>
 * Variable in the message which will be substituted for field label: <em>${fieldLabel}</em>
 *
 * @author Dan Syrstad
 */
public class BeanForm extends Panel
{
    public static final String PARAM_ROWS = "rows";
    
    private static Logger logger = Logger.getLogger(BeanForm.class.getName());
    private static final long serialVersionUID = -7287729257178283645L;
    private static Class<?>[] CONTAINER_CONSTRUCTOR_PARAMS = { String.class, Object.class, BeanMetaData.class, TabMetaData.class };

    private Form form;
    private FormVisitor formVisitor;
    private FeedbackPanel feedback;
    // Wicket ID/HTML ID of field with focus.
    private String focusField = null;
    private BeanPropertyChangeListener listener = new BeanPropertyChangeListener();

    /** Maps components in this form to their properties. */
    private Set<ComponentPropertyMapping> componentPropertyMappings = new HashSet<ComponentPropertyMapping>(200);
    /** Components that should be refreshed on the new Ajax Component update. */ 
    private Set<ComponentPropertyMapping> refreshComponents = new HashSet<ComponentPropertyMapping>(200);
    /** Form submit recursion counter. Zero means we're not validating currently. */
    private int submitCnt = 0;
    private TabbedPanel tabbedPanel = null;

    private Object bean;
    private BeanMetaData beanMetaData;
    private Class<? extends Panel> container;
    
    /**
     * Construct a new BeanForm.
     *
     * @param id the Wicket id for the panel.
     * @param bean the bean to be displayed. This may be an IModel or regular bean object.
     *  The bean may be a List or, if an IModel, a model that returns a List. If so, the bean is display is
     *  displayed using BeanTablePanel. Otherwise BeanGridPanel is used.
     * @param beanMetaData the meta data for the bean. If bean is a List or model of a List, then this must be
     *  the BeanMetaData for a single element (row) of the List. 
     */
    public BeanForm(String id, final Object bean, final BeanMetaData beanMetaData)
    {
        this(id, bean, beanMetaData, null);
    }
    
    /**
     * Construct a new BeanForm.
     *
     * @param id the Wicket id for the panel.
     * @param bean the bean to be displayed. This may be an IModel or regular bean object.
     *  The bean may be a List or, if an IModel, a model that returns a List. If so, the bean is display is
     *  displayed using BeanTablePanel. Otherwise BeanGridPanel is used.
     * @param beanMetaData the meta data for the bean. If bean is a List or model of a List, then this must be
     *  the BeanMetaData for a single element (row) of the List.
     * @param container an optional container to use in place of the default BeanGridPanel or BeanTablePanel. This container must must be a Panel and
     *   implement a constructor of the form: <p>
     *   <code>public Constructor(String id, final Object bean, BeanMetaData beanMetaData, TabMetaData tabMetaData)</code>
     *   <p>
     *   where id = Wicket component ID<br>
     *   bean = the bean, or IModel containing the bean<br>
     *   beanMetaData = the BeanMetaData for bean<br>
     *   tabMetaData = the tab metadata<p>
     *   May be null.
     */
    @SuppressWarnings("serial")
    public BeanForm(String id, final Object bean, final BeanMetaData beanMetaData, final Class<? extends Panel> container)
    {
        super(id);

        this.bean = bean;
        this.beanMetaData = beanMetaData;
        this.container = container;
        
        form = new Form("f") {
            // Track whether the form is in submit processing.
            @Override
            public boolean process()
            {
                ++submitCnt;
                try {
                    return super.process();
                }
                finally {
                    --submitCnt;
                }
            }
        };
        
        form.setOutputMarkupId(true);
        add(form);
        
        String title = beanMetaData.getLabel();
        form.add( new Label("title", title) );
        
        form.add( new Label("beanFormIndicatorErrorLabel", new ResourceModel("beanFormError.msg", "An error occurred on the server. Your session may have timed out.")));
        
        beanMetaData.consumeParameter(PARAM_ROWS);
        
        final HiddenField hiddenFocusField = new HiddenField<String>("focusField", new PropertyModel<String>(this, "focusField"));
        hiddenFocusField.add( new AbstractBehavior() {
            @Override
            public void onComponentTag(Component component, ComponentTag tag)
            {
                tag.put("id", "bfFocusField");
                super.onComponentTag(component, tag);
            }
        });
        
        form.add(hiddenFocusField);
        
        formVisitor = new FormVisitor();

        createTabs(bean, beanMetaData, container);

        // Use a FeedbackMessageFilter to handle messages for multiple BeanForms on a page. This is because messages are stored on the session.
        IFeedbackMessageFilter feedbackFilter = new IFeedbackMessageFilter() {
                public boolean accept(FeedbackMessage message)
                {
                    // If the reporter is a field and this is refreshing because of a non-Ajax form submit, it's very likely that the field has been detached
                    // from its parent because it is in a list view. As a result, findParent doesn't return the BeanForm.
                    Component reporter = message.getReporter();
                    AbstractField reporterField = (AbstractField)(reporter instanceof AbstractField ? reporter : reporter.findParent(AbstractField.class));
                    if (reporterField != null) {
                        return reporterField.getBeanForm().getId().equals(BeanForm.this.getId());
                    }
                    
                    Component parent = (reporter instanceof BeanForm ? reporter : reporter.findParent(BeanForm.class));
                    return reporter == BeanForm.this || parent == null || parent == BeanForm.this;
                }
        };
        
        feedback = new FeedbackPanel("feedback", feedbackFilter);
        feedback.setOutputMarkupId(true);
        form.add(feedback);        

        createGlobalActions();
    }
    
    /**
     * Creates the tabs for the form.
     *
     * @param bean may be a bean or an IModel containing a bean.
     * @param beanMetaData the BeanMetaData.
     * @param container the container class to use. May be null.
     */
    protected void createTabs(final Object bean, final BeanMetaData beanMetaData, final Class<? extends Panel> container)
    {
        List<TabMetaData> tabMetaDataList = beanMetaData.getTabs();
        if (tabMetaDataList.get(0).getId().equals(BeanMetaData.DEFAULT_TAB_ID)) {
            // Single default tab - none explicitly specified. Don't add a tab panel.
            form.addOrReplace( createPanel("tabs", bean, beanMetaData, tabMetaDataList.get(0), container) );
        }
        else {
            List<ITab> tabs = new ArrayList<ITab>();
            for (final TabMetaData tabMetaData : tabMetaDataList) {
                tabs.add( new AbstractTab( new Model<String>(tabMetaData.getLabel()) ) {
                    private static final long serialVersionUID = 1L;
                    public Panel getPanel(String panelId)
                    {
                        return createPanel(panelId, bean, beanMetaData, tabMetaData, container);
                    }
                } );
            }

            // This is a tabbed panel that submits the form and doesn't switch if there are errors.
            tabbedPanel = new TabbedPanel("tabs", tabs) {
                private static final long serialVersionUID = 1L;
                @Override
                protected WebMarkupContainer newLink(String linkId, final int index)
                {
                    return new TabbedPanelSubmitLink(linkId, index);
                }
            };

            form.addOrReplace(tabbedPanel);
        }
    }

    /**
     * Creates global action buttons
     */
    protected void createGlobalActions() {
        // Add bean actions.
        List<ElementMetaData> globalActions = beanMetaData.getGlobalActions();
        form.addOrReplace(new ListView<ElementMetaData>("actions", globalActions) {
            private static final long serialVersionUID = 1L;
            protected void populateItem(ListItem item)
            {
                ElementMetaData element = (ElementMetaData)item.getModelObject();
                item.add( new BeanActionButton("action", element, form, bean) );
            }
        });
    }

    /**
     * Allow applications to recreate the tabs and panels.
     * This is useful to reflect changes in metadata,
     * for example to support dynamic contexts.
     */
    public void recreateFormContents() {
        createTabs(this.bean, this.beanMetaData, this.container);
        createGlobalActions();
    }

    /**
     * Creates the panel for the given tab.
     *
     * @param panelId the Wicket id for the panel component.
     * @param bean may be a bean or an IModel containing a bean.
     * @param beanMetaData the BeanMetaData.
     * @param tabMetaData the TabMetaData.
     * @param containerClass
     * @return a Panel.
     */
    protected Panel createPanel(String panelId, Object bean, BeanMetaData beanMetaData, TabMetaData tabMetaData, Class<? extends Panel> containerClass)
    {
        if (containerClass == null) {
            containerClass = beanMetaData.getContainerClass();
        }
        
        if (containerClass != null) {
            try {
                Constructor<? extends Panel> constructor = containerClass.getConstructor(CONTAINER_CONSTRUCTOR_PARAMS);
                return constructor.newInstance(panelId, bean, beanMetaData, tabMetaData);
            }
            catch (Exception e) {
                throw new RuntimeException("Error instantiating container", e);
            }
        }
        
        boolean isList = (bean instanceof List);
        if (bean instanceof IModel) {
            Object modelBean = ((IModel)bean).getObject();
            isList = (modelBean instanceof List);
        }
        
        if (isList) {
            // BeanTablePanel expects a model. Wrap bean if necessary.
            IModel model;
            if (bean instanceof IModel) {
                model = (IModel)bean;
            }
            else {
                model = new Model<Serializable>((Serializable) bean);
            }
            
            // Get Number of rows from parameters
            int rows = beanMetaData.getIntParameter(PARAM_ROWS, 10);
            return new BeanTablePanel(panelId, model, beanMetaData, rows);
        }

        return new BeanGridPanel(panelId, bean, beanMetaData, tabMetaData);
    }
    
    /**
     * Finds the BeanForm that is the parent of the given childComponent.
     *
     * @param childComponent the child, may be null.
     * 
     * @return the parent BeanForm, or null if childComponent is not part of a BeanForm.
     */
    public static BeanForm findBeanFormParent(Component childComponent)
    {
        if (childComponent == null) {
            return null;
        }
        
        return childComponent.findParent(BeanForm.class);
    }
    
    /**
     * Determines if the BeanForm associated with childComponent is currently in a form
     * submit phase.
     * 
     * @param childComponent the child, may be null.
     * 
     * @return true if the BeanForm is validating, or false if not.
     */
    public static boolean isInSubmit(Component childComponent)
    {
        BeanForm beanForm = findBeanFormParent(childComponent);
        if (beanForm != null) {
            return beanForm.submitCnt > 0;
        }
        
        return false;
    }
    
    /**
     * Rather than using Wicket's required field validation, which doesn't play well with Ajax and forms,
     * allow validation of fields on actions. User must call this from the action method.
     * Adds errors to the page if empty required fields are found. 
     *
     * @return true if validation was successful, else false if errors were found.
     */
    public boolean validateRequired()
    {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        
        // If we have a tabbed panel, we have to go thru each tab and validate it because the components for a tab are only created
        // when the tab is open.
        if (tabbedPanel == null) {
            visitChildren(AbstractField.class, validator);
        }
        else {
            for (ITab tab : tabbedPanel.getTabs()) {
                Panel panel = tab.getPanel("x");
                // Needs to be part of the page for errors.
                getPage().add(panel);
                // Cause ListViews to be populated.
                panel.beforeRender();
                panel.visitChildren(AbstractField.class, validator);
                getPage().remove(panel);
            }
        }
        
        return !validator.errorsFound;
    }
    
    /**
     * Registers the given component with this form. This is usually called by Fields
     * (for example, see {@link AbstractField}) to add the form behavior to their
     * components.
     * 
     * @param component
     * @param beanModel
     * @param element
     */
    public void registerComponent(Component component, BeanPropertyModel beanModel, ElementMetaData element)
    {
        ComponentPropertyMapping mapping = new ComponentPropertyMapping(beanModel, element);
        componentPropertyMappings.add(mapping);
            
        // Make sure we don't register ourself twice.
        if (beanModel != null && beanModel.getBeanForm() == null) {
            // Listen for PropertyChangeEvents on this bean, if necessary.
            // TODO When do we unregister?? Maybe a WeakRef to ourself in the listener? Then listener unregisters
            // TODO if we don't exist anymore.
            element.getBeanMetaData().addPropertyChangeListener(beanModel, listener);
            beanModel.setBeanForm(this);
        }
        
        if (component instanceof MarkupContainer) {
            ((MarkupContainer)component).visitChildren(formVisitor);
        }
        else {
            component.add(new FormSubmitter("onchange"));
        }
    }
    
    
    /**
     * Gets the listener.
     *
     * @return a BeanPropertyChangeListener.
     */
    public BeanPropertyChangeListener getListener()
    {
        return listener;
    }

    /**
     * Allows external app to set the field to receive focus.
     * 
     * @param component the component, may be null to unset the field.
     */
    public void setFocusComponent(Component component)
    {
        setFocusField( component == null ? null : component.getId() );
    }
    
    /**
     * Gets the focusField.
     *
     * @return the focusField.
     */
    public String getFocusField()
    {
        return focusField;
    }

    /**
     * Sets the focusField.
     *
     * @param focusField the focusField to set.
     */
    public void setFocusField(String focusField)
    {
        this.focusField = focusField;
    }

    /**
     * @return true if {@link #refreshComponents(AjaxRequestTarget, Component)} needs to be called.
     */
    public boolean isComponentRefreshNeeded()
    {
        return !refreshComponents.isEmpty();
    }
    
    /**
     * Clears the components that would be refreshed if {@link #refreshComponents(AjaxRequestTarget, Component)} were called.
     */
    public void clearRefreshComponents()
    {
        refreshComponents.clear();
    }
    
    /**
     * Refresh the targetComponent, in addition to any components that need to be updated
     * due to property change events.
     *
     * @param target
     * @param targetComponent the targetComponent, may be null.
     */
    public void refreshComponents(final AjaxRequestTarget target, Component targetComponent)
    {
        if (targetComponent != null) {
            refreshComponent(target, targetComponent);
        }
        
        if (!refreshComponents.isEmpty()) {
            // Refresh components fired from our PropertyChangeListener.
            
            // Visit all children and see if they match the fired events. 
            form.visitChildren( new IVisitor<Component>() {
                public Object component(Component component)
                {
                    Object model = component.getDefaultModel();
                    if (model instanceof BeanPropertyModel) {
                        BeanPropertyModel propModel = (BeanPropertyModel)model;
                        ElementMetaData componentMetaData = propModel.getElementMetaData();
                        for (ComponentPropertyMapping mapping : refreshComponents) {
                            if (mapping.elementMetaData == componentMetaData) {
                                refreshComponent(target, component);
                                break;
                            }
                        }
                    }

                    return IVisitor.CONTINUE_TRAVERSAL;
                }
            });

            refreshComponents.clear();
        }
    }

    private void refreshComponent(final AjaxRequestTarget target, Component targetComponent)
    {
        // Refresh this field. We have to find the parent Field to do this.
        MarkupContainer field;
        if (targetComponent instanceof Field) {
            field = (MarkupContainer)targetComponent;
        }
        else {
            field = targetComponent.findParent(AbstractField.class);
        }
        
        if (field != null) {
            if (!field.getRenderBodyOnly()) {
                target.addComponent(field);
            }
            else {
                // Field is RenderBodyOnly, have to add children individually
                field.visitChildren( new IVisitor<Component>() {
                    public Object component(Component component)
                    {
                        if (!component.getRenderBodyOnly()) {
                            target.addComponent(component);
                        }
                        
                        return IVisitor.CONTINUE_TRAVERSAL;
                    }
                    
                });
            }
        }
        else {
            target.addComponent(targetComponent);
        }
    }

    /**
     * Monitors tab panel submits. 
     */
    private final class TabbedPanelSubmitLink extends SubmitLink
    {
        private static final long serialVersionUID = 1L;

        private final int index;

        private TabbedPanelSubmitLink(String id, int index)
        {
            super(id, form);
            this.index = index;
        }

        @Override
        public void onSubmit()
        {
            if (tabbedPanel.getSelectedTab() != index) {
                tabbedPanel.setSelectedTab(index);
                // TODO this could remember last focus field on the tab and refocus when switching back to the tab
                // TODO Keep separate tab array of focus fields?
                setFocusField(null);
            }

            refreshComponents.clear();
        }
    }

    private final class FormVisitor implements IVisitor<Component>, Serializable
    {
        private static final long serialVersionUID = 4187721147178283645L;
        public Object component(Component component) 
        {
            if (component instanceof FormComponent) {
                boolean addBehavior = true;
                for (IBehavior behavior : component.getBehaviors()) {
                    if (behavior instanceof FormSubmitter) {
                        addBehavior = false;
                        break;
                    }
                }
                
                if (addBehavior) {
                    FormSubmitter behavior = new FormSubmitter("onchange");
                    // Note: Do NOT set a delay. The delay can cause an onchange to be sent AFTER a button submit
                    // which causes the submit button's messages to be erased. <- That was true when we used AjaxSubmitButtons, we don't anymore.
                    //behavior.setThrottleDelay(Duration.milliseconds(250));
                    component.add(behavior);
                    component.add( new SimpleAttributeModifier("onfocus", "bfOnFocus(this)") );
                }
            }
            
            return IVisitor.CONTINUE_TRAVERSAL;
        }
    }

    private final class FormSubmitter extends AjaxFormValidatingBehavior implements Serializable
    {
        private static final long serialVersionUID = 1787721147132483245L;
        private FormSubmitter(String event)
        {
            super(form, event);
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target)
        {
            /*
            // NOTE: The following code fails to clear off field errors that have been corrected.
             
            // Only refresh messages if we have one. Otherwise previous error messages go away on the
            // first field change.
            if (form.getPage().hasFeedbackMessage()) {
                super.onSubmit(target);
            }
            */
            super.onSubmit(target);
            refreshComponents(target, getComponent() );
        }

        @Override
        protected void onError(AjaxRequestTarget target)
        {
            super.onError(target);
            refreshComponents(target, getComponent() );
        }
        
        @Override
        protected IAjaxCallDecorator getAjaxCallDecorator()
        {
            return AjaxBusyDecorator.INSTANCE;
        }
    }
    
    public static final class AjaxBusyDecorator implements IAjaxCallDecorator
    {
        private static final long serialVersionUID = 1287721257178963345L;
        public static final AjaxBusyDecorator INSTANCE = new AjaxBusyDecorator();

        public CharSequence decorateOnFailureScript(CharSequence script)
        {
            return "bfIndicatorError();" + script;
        }

        public CharSequence decorateOnSuccessScript(CharSequence script)
        {
            return "bfIndicatorOff();" + script;
        }

        public CharSequence decorateScript(CharSequence script)
        {
            return "bfIndicatorOn(); " + script;
        }
    }
    
    /**
     * Simple data structure for mapping components and properties. <p>
     */
    private static final class ComponentPropertyMapping implements Serializable
    {
        private static final long serialVersionUID = 2987721358178283623L;
        /** IModel holding the bean. */
        private BeanPropertyModel beanModel;
        private ElementMetaData elementMetaData;
        
        ComponentPropertyMapping(BeanPropertyModel beanModel, ElementMetaData elementMetaData)
        {
            this.beanModel = beanModel;
            this.elementMetaData = elementMetaData;
        }

        /** 
         * {@inheritDoc}
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            int result = 31 + ((beanModel == null) ? 0 : beanModel.hashCode());
            result = 31 * result + ((elementMetaData == null) ? 0 : elementMetaData.hashCode());
            return result;
        }
        
        private Object getBean()
        {
            return beanModel.getBean();
        }

        /** 
         * {@inheritDoc}
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof ComponentPropertyMapping)) {
                return false;
            }

            final ComponentPropertyMapping other = (ComponentPropertyMapping)obj;
            return beanModel == other.beanModel && 
                    (elementMetaData == other.elementMetaData || 
                     (elementMetaData != null && elementMetaData.equals(other.elementMetaData)));
        }
    }
    
    /**
     * Listens to property change events on a bean and adds them to the queue of
     * components to be refreshed. <p>
     */
    public final class BeanPropertyChangeListener implements PropertyChangeListener, Serializable
    {
        private static final long serialVersionUID = 3387721147224283645L;
        public void propertyChange(PropertyChangeEvent evt)
        {
            // Find matching component
            Object bean = evt.getSource();
            String propName = evt.getPropertyName();
            for (ComponentPropertyMapping mapping : componentPropertyMappings) {
                if (bean == mapping.getBean() && propName.equals(mapping.elementMetaData.getPropertyName())) {
                    BeanForm.this.refreshComponents.add(mapping);
                }
            }
        }
    }
    
    /**
     * Validates required fields on the form and sets an error message on the component if necessary.
     */
    private final class RequiredFieldValidator implements IVisitor<Component>
    {
    	private class FieldLabel implements Serializable
        {
            private static final long serialVersionUID = 1L;
    		String fieldLabel;
    		public FieldLabel(String fieldLabel){this.fieldLabel = fieldLabel;}
			public String getFieldLabel() {return fieldLabel;}
			public void setFieldLabel(String fieldLabel) {this.fieldLabel = fieldLabel;}
    	}

    	boolean errorsFound = false;
        
        public Object component(Component component)
        {
            AbstractField field = (AbstractField)component;
            if (field.isRequiredField() && Strings.isEmpty(field.getDefaultModelObjectAsString())) {
            	FieldLabel fieldName = new FieldLabel(field.getElementMetaData().getLabel());
            	StringResourceModel labelModel = new StringResourceModel("wicketwebbeans.BeanForm.fieldIsRequired", field.getElementMetaData().getBeanMetaData().getComponent(), new Model<FieldLabel>(fieldName), "${fieldLabel} is required");
            	BeanForm.this.error(labelModel.getObject().toString());
                errorsFound = true;
            }
            
            return CONTINUE_TRAVERSAL;
        }
    } 
}
