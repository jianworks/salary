<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>

<script language=JavaScript src="js/menu.js" type=text/javascript></script>

<%  
  List aplist0 = (ArrayList)request.getAttribute("aplist0");
  List aplist1 = (ArrayList)request.getAttribute("aplist1");
  List aplist2 = (ArrayList)request.getAttribute("aplist2");
  List aplist3 = (ArrayList)request.getAttribute("aplist3");
  //List aplist4 = (ArrayList)request.getAttribute("aplist4");
%>
		<TABLE width=223 cellSpacing=0 cellPadding=0 border=0>
			<TR>
				<TD width=222 height=31 background="images/buttunBg.gif" align=left
					valign=middle><SPAN class="welcome">&nbsp;&nbsp;${name}  您好!</SPAN>
				</TD>
			</TR>
			<TR>
				<TD width=222 height=10 align=left valign=middle>&nbsp;</TD>
			</TR>
			<TR>
				<TD width=222 align=left valign=middle>
					<div id="dhtmlgoodies_slidedown_menu">
						<ul>
						<% if (aplist0.size()>0) { %>
						<li><a href="#">&nbsp;系統管理功能</a>
							<ul>
								<%	for (int i = 0; i < aplist0.size(); i++) {
										String[] ap = (String[]) aplist0.get(i);	%>
								<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
								<%	}	%>
							</ul>		
						</li>
						<% } %>
						<% if (aplist1.size()>0) { %>
						<li><a href="#">&nbsp;員工及薪資資料建檔</a>
							<ul>
								<%	for (int i = 0; i < aplist1.size(); i++) {
										String[] ap = (String[]) aplist1.get(i);	%>
								<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
								<%	}	%>
							</ul>		
						</li>
						<% } %>
						<% if (aplist2.size()>0) { %>
						<li><a href="#">&nbsp;報表產生列印</a>
							<ul>
								<%	for (int i = 0; i < aplist2.size(); i++) {
										String[] ap = (String[]) aplist2.get(i);	%>
								<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
								<%	}	%>
								<li><A href='<%=response.encodeURL("report.do?action=employeeList")%>' class="navlink">&nbsp;&nbsp;員工基本資料報表</A></li>
							</ul>		
						</li>
						<% } %>
						<% if (aplist3.size()>0) { %>
						<li><a href="#">&nbsp;勞健保加退產生列印</a>
							<ul>
								<%	for (int i = 0; i < aplist3.size(); i++) {
										String[] ap = (String[]) aplist3.get(i);	%>
								<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
								<%	}	%>
							</ul>		
						</li>
						<% } %>						
						<li><a href="#">&nbsp;成立勞健保投保單位</a>
							<ul>
								<li><A href='<%=response.encodeURL("form.do?action=paForm")%>' class="navlink">&nbsp;&nbsp;代辦委託書</A></li>
								<li><A href='<%=response.encodeURL("form.do?action=startForm")%>' class="navlink">&nbsp;&nbsp;三合一成立申請書</A></li>
								<li><A href='<%=response.encodeURL("form.do?action=addForm2")%>' class="navlink">&nbsp;&nbsp;三合一加保申報表</A></li>
								<li><A href='<%=response.encodeURL("form.do?action=declationForm")%>' class="navlink">&nbsp;&nbsp;勞保切結書</A></li>
								<li><A href='<%=response.encodeURL("form.do?action=proclamationForm")%>' class="navlink">&nbsp;&nbsp;健保切結書</A></li>
							</ul>		
						</li>
						<li><a href='<%=response.encodeURL("reportFile.do?action=list")%>' class="navlink"/>&nbsp;&nbsp;報表檔案下載</li>
						</ul>
					</div>
				</TD>
			</TR>			
		</TABLE>


