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

package com.googlecode.wicketwebbeans.fields;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.Strings;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.googlecode.wicketwebbeans.model.ElementMetaData;

/**
 * Date/Time Field component. Implemented as a text field combined with a DatePicker.
 * <p>
 * 
 * The enclosing component's properties file may define standard date/time formats 
 * using the following keys:
 * <ul>
 * <li><code>YUIDateField.date.format</code> - Format for java.sql.Date, java.sql.Time, java.sql.Timestamp or java.util.Date.</li>
 * <li><code>YUIDateField.datetz.format</code> - Format for java.util.Calendar types.</li>
 * </ul>
 * 
 *  TODO This is a work in-progress. Note that the YUI Calendar doesn't support time. 
 * 
 * @author Dan Syrstad
 */
public class YUIDateField extends AbstractField
{
    private static final long serialVersionUID = 2432855853285841120L;

    public static final String DATE_FMT_STR = "yyyy-MM-dd";
    public static final String DATE_ZONE_FMT_STR = DATE_FMT_STR + " z";
    
    private static final String DATE_TIME_FIELD_PREFIX = "YUIDateField.";
    private static final String FORMAT_SUFFIX = ".format";
    
    private String fmt;
    private Class<?> type;

    /**
     * Construct a new YUIDateField.
     *
     * @param id the Wicket id for the editor.
     * @param model the model.
     * @param metaData the meta data for the property.
     * @param viewOnly true if the component should be view-only.
     */
    public YUIDateField(String id, IModel<Date> model, ElementMetaData metaData, boolean viewOnly)
    {
        super(id, model, metaData, viewOnly);
        
        type = metaData.getPropertyType();
        boolean displayTz = false;
        Component metaDataComponent = metaData.getBeanMetaData().getComponent();
        Localizer localizer = metaDataComponent.getLocalizer();
        if (Time.class.isAssignableFrom(type) ||
            java.sql.Date.class.isAssignableFrom(type) ||
            Date.class.isAssignableFrom(type) || 
            Timestamp.class.isAssignableFrom(type)) {
            fmt = localizer.getString(DATE_TIME_FIELD_PREFIX + "date" + FORMAT_SUFFIX, metaDataComponent, DATE_FMT_STR);
        }
        else if (Calendar.class.isAssignableFrom(type)) {
            fmt = viewOnly ? localizer.getString(DATE_TIME_FIELD_PREFIX + "datetz" + FORMAT_SUFFIX, metaDataComponent, DATE_ZONE_FMT_STR) : 
                             localizer.getString(DATE_TIME_FIELD_PREFIX + "date" + FORMAT_SUFFIX, metaDataComponent, DATE_FMT_STR);
            displayTz = true;
        }
        else { 
            throw new RuntimeException("YUIDateField does not handle " + type);
        }
        
        String customFmt = getFormat();
        if (customFmt != null) {
            fmt = customFmt;
        }
        
        Fragment fragment;
        if (viewOnly) {
            fragment = new Fragment("frag", "viewer");
            fragment.add( DateLabel.withConverter("date", model, new InternalDateConverter()) );
        }
        else {
            fragment = new Fragment("frag", "editor");

            FormComponent dateField = DateTextField.withConverter("dateTextField", model, new InternalDateConverter());
            setFieldParameters(dateField);
            fragment.add(dateField);
            
            dateField.add(new DatePicker() {
                private static final long serialVersionUID = 1L;
                @Override
                protected boolean enableMonthYearSelection()
                {
                    return false;
                }

                @Override
                protected CharSequence getIconUrl()
                {
                    return RequestCycle.get().urlFor(new ResourceReference(YUIDateField.class, "calendar.gif"));
                }
            });
            
            if (displayTz) {
                DateLabel tzLabel = DateLabel.withConverter("timezone", model, new TimeZoneConverter() );
                fragment.add(tzLabel);
            }
            else {
                fragment.add( new Label("timezone", "").setVisible(false) );
            }
        }

        add(fragment);
    }

    private final class InternalDateConverter extends DateConverter 
    {
        private static final long serialVersionUID = 1L;

        public InternalDateConverter()
        {
            super(false);
        }
        
        public String convertToString(Object value, Locale locale)
        {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            if (value instanceof Calendar) {
                Calendar cal = (Calendar)value;
                dateFmt.setTimeZone( cal.getTimeZone() );
                return dateFmt.format( cal.getTime() );
            }
            if (value instanceof Date) {
                dateFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
                return dateFmt.format((Date)value);
            }
            return null;
        }
        
        public Date convertToObject(String value, Locale locale)
        {
            if (Strings.isEmpty(value)) {
                return null;
            }
            
            // Convert String to Calendar or sql.Date/Time/Timestamp
            // First convert it to a milliseconds. We think it's GMT because no TZ is specified in the String.
            long date;
            try {
                DateTimeFormatter dateFmt = getFormat().withZone(DateTimeZone.UTC);
                date = dateFmt.parseMillis(value);
            }
            catch (IllegalArgumentException e) {
                throw new ConversionException("Cannot convert '" + value + "' to a Date.")
                    .setSourceValue(value)
                    .setTargetType(type)
                    .setConverter(this)
                    .setLocale(locale);                
            }
            
            if (Timestamp.class == type) {
                return new Timestamp(date);
            }
            
            if (java.sql.Date.class == type) {
                return new java.sql.Date(date);
            }
            
            if (Time.class == type) {
                return new Time(date);
            }
            
            if (Date.class == type) {
                return new Date(date);
            }
            
            if (Calendar.class.isAssignableFrom(type)) {
                Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("GMT") );
                cal.setTimeInMillis(date);
                return cal.getTime();
            }

            throw new RuntimeException("Don't know how to convert a String to " + type);
        }
        
        protected DateTimeFormatter getFormat()
        {
            return DateTimeFormat.forPattern(fmt);
        }
        
        public String getDatePattern()
        {
            return fmt;
        }
    }

    private final class TimeZoneConverter extends DateConverter 
    {
        private static final long serialVersionUID = 1L;

        public TimeZoneConverter()
        {
            super(true);
        }
        
        public String convertToString(Object value, Locale locale)
        {
            if (value instanceof Calendar) {
                Calendar cal = (Calendar)value;
                TimeZone zone = cal.getTimeZone(); 
                return zone.getDisplayName( zone.inDaylightTime(cal.getTime()), TimeZone.SHORT, locale);
            }

            throw new RuntimeException("Don't know how to convert " + value.getClass() + " to a String");
        }

        public Date convertToObject(String value, Locale locale)
        {
            return null;
        }
        
        protected DateTimeFormatter getFormat()
        {
            return DateTimeFormat.forPattern(fmt);
        }
        
        public String getDatePattern()
        {
            return fmt;
        }
    }
}