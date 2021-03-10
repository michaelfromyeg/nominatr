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
  <p>This (auto-generated) email is to update you on the status of your nomination campaign and to help you make sure you get enough nominations. Once you reach 15 nominations or more, you won't see this email again, but do read it carefully so you don't miss any of the key election information! (Our apologies in advance if you've already been notified about receiving 15 or more nominations; this email should serve as a useful reminder though to submit your blurb and headshot, as they are due soon!)</p>
  <#list count>
    <#items as columnName1, columnName2>
    <p>You currently have ${columnName1} nominations; you need ${columnName2} more nominations to be eligible to run. Nominations are due Wednesday, Mar. 10th at 12:00 PM. More information available <a href="https://docs.google.com/document/d/1bVl4k_xIeEMMbfoY61QJlq15obfVdXnKMXvfiEFpKMk/edit">here</a>.</p>
    </#items>
  </#list>
  <p><b>Note: </b>Once you reach 15 or more nominations, please submit a 200 word biography with a 300 DPI headshot to <a href="mailto:elections@sus.ubc.ca">elections@sus.ubc.ca</a> (i.e., reply to this email). All other key election resources, information, and the timeline for the Spring election can be found at <a href="https://docs.google.com/document/u/1/d/e/2PACX-1vS4NpjoKjP_Dd-wGkkX77tSDY5lFr-O4zK5foOhAcgWY67AyhpkmY7qEr-Byg0_EJUnePp9jPFgfLVs/pub?urp=gmail_link&gxids=7628" target="_blank">this</a> webpage.</p>
  <p>Here's where you stand at the moment.</p>
  <table style="border: 1px solid black; border-collapse: collapse;">
    <caption>Summary</caption>
    <tr>
      <th style="border: 1px solid black; padding: 5px;">Nominee</th>
      <th style="border: 1px solid black; padding: 5px;">Tally</th>
    </tr>
    <!-- Nominees should be a single element list -->
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
  <p>If you have any questions, or anything looks off, don't hestiate to reach out to the Elections team.</p>
  <p>Disclaimer: this project was developed by the SUS Elections Administration team to act as a way of facilitating nominations during a remote election. It is completely open source; you may view the source code <a href="https://github.com/michaelfromyeg/qualtrics-lambda">here</a>. No data is held by the program, as it only relays data from Qualtrics. The project itself is hosted on Canadian servers.</p>
  <br />
  <p>Sincerely,</p>
  <p><b>Michael, Andrew, Arian, and Christina</b></p>
  <p><i>Elections Administrators</i>
  <p>UBC Science Undergraduate Society</p>
  <p><a href="mailto:elections@sus.ubc.ca">elections@sus.ubc.ca</a> | <a href="https://sus.ubc.ca">www.sus.ubc.ca</a></p>
  <img src="https://i.ibb.co/Vqr7J4T/sus-logo.png" width="300" height="75" alt="SUS Logo" />
</body>
</html>