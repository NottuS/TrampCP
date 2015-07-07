<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:text maxlength="19" size="20" name="bemImovelForm" property="areaTerreno" styleId="areaTerreno" onkeyup="javascript:DigitaNumMascara(this, -1);" onchange="javascript:habilitarAlterar();" />