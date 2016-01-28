<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<head>
  <title>rodeo - Job status</title>
  <meta http-equiv="refresh" content="3" />
  <link rel="stylesheet" type="text/css" href="/rodeo/css/rodeo.css" />
</head>
<body>
<h1>Job Status</h1>
<table>
  <tr><th>Job ID</th><th>Status</th></tr>
<xsl:for-each select="jobsResult/jobs">
  <tr>
    <td>
      <xsl:element name="a">
        <xsl:attribute name="href">
          <xsl:value-of select="link"/>
        </xsl:attribute>
        <xsl:value-of select="identifier"/>
      </xsl:element>
      </td>
    <td><xsl:value-of select="status"/></td>
  </tr>
</xsl:for-each>
</table>
</body>
</html>
