<html>
<head>
  <title>SUS Elections Update</title>
</head>
<body>
  <p>Hey SUS Elections Administrators!</p>
  <p>This email is to update you on the status of the election, and to help you make sure everything is running smoothly.</p>
  <p>A current tally has been attached, but here are the election standings so far (formatted for your viewing pleasure). There's also a log of everything that happened when this code ran, in case something goes wrong.</p>
  <table style="border: 1px solid black; border-collapse: collapse;">
    <caption>Nominees</caption>
    <tr>
      <th style="border: 1px solid black; padding: 5px;">Nominee</th>
      <th style="border: 1px solid black; padding: 5px;">Tally</th>
    </tr>
    <#list nominees>
      <#items as column1A, column2A>
      <tr>
        <td style="border: 1px solid black; padding: 5px;">
          ${column1A}
        </td>
        <td style="border: 1px solid black; padding: 5px; text-align: center; vertical-align: middle;">
          ${column2A}
        </td>
      </tr>
      </#items>
    </#list>
  </table>
  <br />
  <table style="border: 1px solid black; border-collapse: collapse;">
    <caption>Nominators</caption>
    <tr>
      <th style="border: 1px solid black; padding: 5px;">Nominator</th>
      <th style="border: 1px solid black; padding: 5px;">For</th>
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
  <br />
  <p>Sincerely,</p>
  <p><b>Michael, Andrew, Arian, and Christina</b></p>
  <p><i>Elections Administrators</i>
  <p>UBC Science Undergraduate Society</p>
  <p><a href="mailto:elections@sus.ubc.ca">elections@sus.ubc.ca</a> | <a href="https://sus.ubc.ca">www.sus.ubc.ca</a></p>
  <img src="https://via.placeholder.com/200x150" />
</body>
</html>