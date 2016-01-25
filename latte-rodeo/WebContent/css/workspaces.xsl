<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<head>
		<title>rodeo - Job status</title>
		<link rel="stylesheet" type="text/css" href="/rodeo/css/rodeo.css" />
	</head>
	<body>
		<h1>Workspace</h1>
		<h2>
			actual:
			<xsl:value-of select="dirListResult/path" />
		</h2>
		<h2>
			<xsl:element name="a">
				<xsl:attribute name="href">
         				<xsl:value-of select="dirListResult/parent" />
        			</xsl:attribute>
				<xsl:text>go back</xsl:text>
			</xsl:element>
		</h2>
		<table>
			<tr>
				<th>Path</th>
				<th>last modified</th>
			</tr>
			<xsl:for-each select="dirListResult/item">
				<tr>
					<td>
						<xsl:element name="a">
							<xsl:attribute name="href">
         				<xsl:value-of select="link" />
        			</xsl:attribute>
							<xsl:if test="not(directory = 'true')">
								<xsl:attribute name="target">
									<xsl:text>_blank</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="name" />
						</xsl:element>
					</td>
					<td>
						<xsl:value-of select="lastModified" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</body>
</html>
