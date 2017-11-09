package com.sky.mybatis.generator;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class PaginationPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// addfield, getter, setter for limit clause

		addLimit(topLevelClass, introspectedTable, "limitStart");

		addLimit(topLevelClass, introspectedTable, "limitSize");
		// TODO Auto-generated method stub
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {

		CommentGenerator commentGenerator = context.getCommentGenerator();

		Field field = new Field();

		field.setVisibility(JavaVisibility.PROTECTED);

		field.setType(FullyQualifiedJavaType.getIntInstance());

		field.setName(name);

		field.setInitializationString("-1");

		commentGenerator.addFieldComment(field, introspectedTable);

		topLevelClass.addField(field);

		char c = name.charAt(0);

		String camel = Character.toUpperCase(c) + name.substring(1);

		Method method = new Method();

		method.setVisibility(JavaVisibility.PUBLIC);

		method.setName("set" + camel);

		method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));

		method.addBodyLine("this." + name + "=" + name + ";");

		commentGenerator.addGeneralMethodComment(method, introspectedTable);

		topLevelClass.addMethod(method);

		method = new Method();

		method.setVisibility(JavaVisibility.PUBLIC);

		method.setReturnType(FullyQualifiedJavaType.getIntInstance());

		method.setName("get" + camel);

		method.addBodyLine("return " + name + ";");

		commentGenerator.addGeneralMethodComment(method, introspectedTable);

		topLevelClass.addMethod(method);

	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		
		addLimitElement(element);
		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		addLimitElement(element);
		
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}

	private void addLimitElement(XmlElement element) {
		// LIMIT 5,10; // 检索记录行 6-15
        XmlElement isNotNullElement= new XmlElement("if");//$NON-NLS-1$

        isNotNullElement.addAttribute(new Attribute("test","limitStart != null and limitStart >=0"));//$NON-NLS-1$ //$NON-NLS-2$

        isNotNullElement.addElement(new TextElement("limit #{limitStart},#{limitSize}"));

        element.addElement(isNotNullElement);
        // LIMIT 5;//检索前 5个记录行
	}
	
	

}
