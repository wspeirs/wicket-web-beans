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

package com.googlecode.wicketwebbeans.model.annotations;

import java.util.List;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicketwebbeans.fields.BeanGridField;
import com.googlecode.wicketwebbeans.fields.BeanInlineField;
import com.googlecode.wicketwebbeans.fields.EmptyField;
import com.googlecode.wicketwebbeans.fields.TextAreaField;
import com.googlecode.wicketwebbeans.model.BeanMetaData;
import com.googlecode.wicketwebbeans.model.ElementMetaData;
import com.googlecode.wicketwebbeans.model.TabMetaData;

/**
 * Tests BeanMetaData, ElementMetaData, and TabMetaData from an annotation perspective. <p>
 * 
 * @author Dan Syrstad
 */
public class AnnotationTest extends TestCase
{
    
    /**
     * Construct a AnnotationTest. 
     *
     * @param name
     */
    public AnnotationTest(String name)
    {
        super(name);
    }

    private void assertElement(BeanMetaData beanMetaData, ElementMetaData element, ElementInfo expected)
    {
        assertEquals(expected.propName, expected.propName, element.getPropertyName());
        assertEquals(expected.propName, expected.viewOnly, element.isViewOnly());
        assertEquals(expected.propName, expected.propName.startsWith("action."), element.isAction());
        assertSame(beanMetaData, element.getBeanMetaData());
        if (element.isAction()) {
            assertEquals(expected.propName, expected.propName.substring(expected.propName.indexOf('.') + 1) , element.getActionMethodName());
        }
        else {
            assertNull(expected.propName, element.getActionMethodName());
        }
        
        assertEquals(expected.propName, expected.elementType, element.getElementTypeName() );
        assertEquals(expected.propName, expected.fieldType, element.getFieldType() );
        assertEquals(expected.propName, expected.label, element.getLabel());
        assertNotNull(expected.propName, element.getLabelComponent("test"));
        assertEquals(expected.propName, expected.labelImage, element.getLabelImage());
        assertEquals(expected.propName, expected.tabId, element.getTabId());
    }
    
    /**
     * Tests basic Annotations
     */
    public void testBasicAnnotations()
    {
        WicketTester tester = new WicketTester();
        tester.startPage(AnnotationTestPage.class);
        Page page = tester.getLastRenderedPage();
        
        BeanMetaData beanMetaData = new BeanMetaData(AnnotationTestBean.class, null, page, null, false);
        // Check parameters, elements, element parameters, tabs, tab elements.
        assertFalse( beanMetaData.isViewOnly() );
        assertTrue( beanMetaData.isDisplayed() );
        // Test that ${} substitution works and that the properties file is referenced.
        assertEquals("My Experiment Title", beanMetaData.getLabel() );
        
        ElementInfo[] expectedProps = { 
            new ElementInfo("action.addRow", false, null, null, "Add Row", null, "listOfBeans"),
            new ElementInfo("action.save", false, null, null, "Save", null, "nameInfo"),
            // firstName was explicitly overridden as not viewOnly.
            new ElementInfo("gender", false, null, null, "Gender", null, "miscInfo"),
            new ElementInfo("beans", true, null, null, "Beans", null, "listOfBeans"),
            new ElementInfo("firstName", false, null, null, "First Name", null, "nameInfo"),
            new ElementInfo("EMPTY:28", true, EmptyField.class.getName(), null, "", null, "miscInfo"),
            new ElementInfo("age", false, null, null, "Age", null, "miscInfo"),
            new ElementInfo("lastName", false, null, null, "Last Name", null, "nameInfo"),
            new ElementInfo("operand1", false, null, null, "Operand 1", null, "miscInfo"),
            new ElementInfo("EMPTY:28", true, EmptyField.class.getName(), null, "", null, "nameInfo"),
            new ElementInfo("activePrimitive", false, null, null, "Active Primitive", null, "nameInfo"),
            new ElementInfo("operand2", false, null, null, "Operand 2", null, "miscInfo"),
            new ElementInfo("color", false, null, null, "Color", null, "nameInfo"),
            new ElementInfo("result", true, null, null, "Result", null, "miscInfo"),
            new ElementInfo("inlineBean", false, BeanInlineField.class.getName(), null, "Inline Bean", null, "nameInfo"),
            new ElementInfo("palette", false, null, AnnotationTestBean.ColorEnum.class.getName(), "Palette", null, "miscInfo"),
            new ElementInfo("dateTimestamp", false, null, null, "Date Timestamp", null, "nameInfo"),
            new ElementInfo("palette2", true, null, AnnotationTestBean.ColorEnum.class.getName(), "Palette 2", null, "miscInfo"),
            new ElementInfo("blockBean", false, BeanGridField.class.getName(), null, "Block Bean", null, "nameInfo"),
            new ElementInfo("description", false, TextAreaField.class.getName(), null, "Description", null, "miscInfo"),
            new ElementInfo("testBean2", false, null, null, "Test Bean 2", null, "nameInfo"),
            new ElementInfo("popupBean", false, null, null, "Popup Bean", null, "nameInfo"),
            new ElementInfo("action.cancel", false, null, null, "Cancel", null, null),
            new ElementInfo("action.doIt", false, null, null, "Do It", null, null),
            new ElementInfo("dateOnly", false, null, null, "Date Only", null, "nameInfo"),
            new ElementInfo("isActive", false, null, null, "Is Active", null, "nameInfo"),
            new ElementInfo("savingsAmount", false, null, null, "Savings Amount", null, "nameInfo"),
            new ElementInfo("startDate", false, null, null, "Start Date", null, "nameInfo"),
            new ElementInfo("timeOnly", false, null, null, "Time Only", null, "nameInfo"),
        };

        assertEquals(expectedProps.length, beanMetaData.getDisplayedElements().size());
        
        int elementIdx = 0;
        for (ElementMetaData element : beanMetaData.getDisplayedElements()) {
            //System.out.println("new ElementInfo(\"" + element.getPropertyName() + "\", " + element.isViewOnly() + ", \"" + element.getFieldType() + "\", \"" + element.getElementTypeName() + "\", \"" + element.getLabel() + "\", \"" + element.getLabelImage() + "\", \"" + element.getTabId() + "\"),");
            assertElement(beanMetaData, element, expectedProps[elementIdx]);
            ++elementIdx;
        }
        
        // action.save (second element) should have a parameter of colspan: 3
        assertEquals(Integer.valueOf(3), beanMetaData.getDisplayedElements().get(1).getIntegerParameter("colspan"));
        
        List<TabMetaData> tabs = beanMetaData.getTabs();
        assertEquals(3, tabs.size());
        
        assertEquals("nameInfo", tabs.get(0).getId());
        assertEquals("Name Info", tabs.get(0).getLabel());
        assertEquals(16, beanMetaData.getTabElements(tabs.get(0)).size());
        
        assertEquals("miscInfo", tabs.get(1).getId());
        assertEquals("Miscellaneous", tabs.get(1).getLabel());
        assertEquals(9, beanMetaData.getTabElements(tabs.get(1)).size());
        
        assertEquals("listOfBeans", tabs.get(2).getId());
        assertEquals("List Of Beans", tabs.get(2).getLabel());
        assertEquals(2, beanMetaData.getTabElements(tabs.get(2)).size());
    }
    
    /**
     * Tests a context. Implicitly extends the default context.
     */
    public void testContext()
    {
        WicketTester tester = new WicketTester();
        tester.startPage(AnnotationTestPage.class);
        Page page = tester.getLastRenderedPage();
        
        // Use the "view" context
        BeanMetaData beanMetaData = new BeanMetaData(AnnotationTestBean.class, "view", page, null, false);
        // Check parameters, elements, element parameters, tabs, tab elements.
        assertTrue( beanMetaData.isViewOnly() );
        assertTrue( beanMetaData.isDisplayed() );
        assertEquals("Bean View", beanMetaData.getLabel() );
        
        ElementInfo[] expectedProps = { 
            new ElementInfo("action.addRow", true, null, null, "Add Row", null, "listOfBeans"),
            new ElementInfo("action.save", true, null, null, "Save", null, "nameInfo"),
            // firstName was explicitly overridden as not viewOnly.
            new ElementInfo("gender", true, null, null, "Gender", null, "miscInfo"),
            new ElementInfo("beans", true, null, null, "Beans", null, "listOfBeans"),
            new ElementInfo("firstName", false, null, null, "First Name", null, "nameInfo"),
            new ElementInfo("EMPTY:28", true, EmptyField.class.getName(), null, "", null, "miscInfo"),
            new ElementInfo("age", true, null, null, "Age", null, "miscInfo"),
            new ElementInfo("lastName", true, null, null, "Last Name", null, "nameInfo"),
            new ElementInfo("operand1", true, null, null, "Operand 1", null, "miscInfo"),
            new ElementInfo("EMPTY:28", true, EmptyField.class.getName(), null, "", null, "nameInfo"),
            new ElementInfo("activePrimitive", true, null, null, "Active Primitive", null, "nameInfo"),
            new ElementInfo("operand2", true, null, null, "Operand 2", null, "miscInfo"),
            new ElementInfo("color", true, null, null, "Color", null, "nameInfo"),
            new ElementInfo("result", true, null, null, "Result", null, "miscInfo"),
            new ElementInfo("inlineBean", true, BeanInlineField.class.getName(), null, "Inline Bean", null, "nameInfo"),
            new ElementInfo("palette", true, null, AnnotationTestBean.ColorEnum.class.getName(), "Palette", null, "miscInfo"),
            new ElementInfo("dateTimestamp", true, null, null, "Date Timestamp", null, "nameInfo"),
            new ElementInfo("blockBean", true, BeanGridField.class.getName(), null, "Block Bean", null, "nameInfo"),
            new ElementInfo("description", true, TextAreaField.class.getName(), null, "Description", null, "miscInfo"),
            new ElementInfo("testBean2", true, null, null, "Test Bean 2", null, "nameInfo"),
            new ElementInfo("popupBean", true, null, null, "Popup Bean", null, "nameInfo"),
            new ElementInfo("action.cancel", true, null, null, "Cancel", null, null),
            new ElementInfo("action.doIt", true, null, null, "Do It", null, null),
            new ElementInfo("dateOnly", true, null, null, "Date Only", null, "nameInfo"),
            new ElementInfo("isActive", true, null, null, "Is Active", null, "nameInfo"),
            new ElementInfo("savingsAmount", true, null, null, "Savings Amount", null, "nameInfo"),
            new ElementInfo("startDate", true, null, null, "Start Date", null, "nameInfo"),
            new ElementInfo("timeOnly", true, null, null, "Time Only", null, "nameInfo"),
        };

        assertEquals(expectedProps.length, beanMetaData.getDisplayedElements().size());
        
        int elementIdx = 0;
        for (ElementMetaData element : beanMetaData.getDisplayedElements()) {
            //System.out.println("new ElementInfo(\"" + element.getPropertyName() + "\", " + element.isViewOnly() + ", \"" + element.getFieldType() + "\", \"" + element.getElementTypeName() + "\", \"" + element.getLabel() + "\", \"" + element.getLabelImage() + "\", \"" + element.getTabId() + "\"),");
            assertElement(beanMetaData, element, expectedProps[elementIdx]);
            ++elementIdx;
        }
        
        // action.save (second element) should have a parameter of colspan: 4
        assertEquals(Integer.valueOf(4), beanMetaData.getDisplayedElements().get(1).getIntegerParameter("colspan"));
        
        List<TabMetaData> tabs = beanMetaData.getTabs();
        assertEquals(3, tabs.size());
        
        assertEquals("nameInfo", tabs.get(0).getId());
        assertEquals("Name Info", tabs.get(0).getLabel());
        assertEquals(16, beanMetaData.getTabElements(tabs.get(0)).size());
        
        assertEquals("miscInfo", tabs.get(1).getId());
        assertEquals("Miscellaneous", tabs.get(1).getLabel());
        assertEquals(8, beanMetaData.getTabElements(tabs.get(1)).size());
        
        assertEquals("listOfBeans", tabs.get(2).getId());
        assertEquals("List Of Beans", tabs.get(2).getLabel());
        assertEquals(2, beanMetaData.getTabElements(tabs.get(2)).size());
    }
    
    private static final class ElementInfo
    {
        String propName;
        boolean viewOnly;
        String fieldType;
        String elementType;
        String label;
        String labelImage;
        String tabId;
 
        ElementInfo(String propName, boolean viewOnly, String fieldType, String elementType, String label, String labelImage, String tabId)
        {
            this.propName = propName;
            this.viewOnly = viewOnly;
            this.fieldType = fieldType;
            this.elementType = elementType;
            this.label = label;
            this.labelImage = labelImage;
            this.tabId = tabId;
        }
    }
}
