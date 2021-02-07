<html>
<head>
  <title>SUS Elections Update</title>
</head>
<body>
  <#list name>
    <#items as columnName1, columnName2>
    <p>Hi ${columnName1},</p>
    </#items>
  </#list>
  <p>This (auto-generated) email is to act as a receipt of your recent nomination.</p>
  <p>Here's what we received.</p>
  <table style="border: 1px solid black; border-collapse: collapse;">
    <caption>Summary</caption>
    <tr>
      <th style="border: 1px solid black; padding: 5px;">Nominator</th>
      <th style="border: 1px solid black; padding: 5px;">Nominee</th>
    </tr>
    <#list nominators>
      <#items as column1B, column2B>
      <tr>
        <td style="border: 1px solid black; padding: 5px;">
          ${column1B}
        </td>
        <td style="border: 1px solid black; padding: 5px;">
          ${column2B}
        </td>
      </tr>
      </#items>
    </#list>
  </table>
  <p>If you have any questions, or anything looks off, don't hestiate to reach out!</p>
  <p style="font-size: small">Disclaimer: this project was developed by SUS to act as a way of facilitating nominations during a remote election. It is completely open-source; you may view the source-code <a href="https://github.com/michaelfromyeg/qualtrics-lambda">here</a>. No data is held by the program, as it only relays data from Qualtrics. The project itself is hosted on Canadian servers.</p>
  <br />
  <p>Sincerely,</p>
  <p><b>Michael, Andrew, Arian, and Christina</b></p>
  <p><i>Elections Administrators</i>
  <p>UBC Science Undergraduate Society</p>
  <p><a href="mailto:elections@sus.ubc.ca">elections@sus.ubc.ca</a> | <a href="https://sus.ubc.ca">www.sus.ubc.ca</a></p>
  <img src="https://via.placeholder.com/200x150" />
</body>
</html>