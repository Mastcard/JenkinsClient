#Jenkins java client

<p>
This java client is used to log on a Jenkins server and execute following actions :
<ol>
    <li>copying a list of jobs (RegExp) replacing a list of patterns</li>
    <li>removing a list of jobs (RegExp) or all the unused jobs (created but not used by any view)</li>
</ol>

New actions could be added later.
</p>

<h3>User guide</h3>
<p>
  <ol>
    <li>Copying jobs replacing patterns</li>
      <b>Command line :</b>
      <ul>
        <li>java -jar jenkins_client.jar -server http://HOST:PORT -username USERNAME -password PASSWORD -copyJobsReplacingPatterns [regexp to list jobs] [before1/after1,before2/after2,before3/after3,...]</li>
      </ul>
      <b>For instance :</b>
      <ul>
        <li>java -jar jenkins_client.jar -server http://myServer:myPort -username user -password pass -copyJobsReplacingPatterns .*Compilation.* Compilation/Tests,machineA/Bcomputer</li>
      </ul>
      </br>
    <li>Removing a list of jobs</li>
      <b>Command line :</b>
      <ul>
        <li>java -jar jenkins_client.jar -server http://HOST:PORT -username USERNAME -password PASSWORD -removeJobs [regexp to list jobs]</li>
        <li>java -jar jenkins_client.jar -server http://HOST:PORT -username USERNAME -password PASSWORD -removeJobs unused</li>
      </ul>
      <b>For instance :</b>
      <ul>
        <li>java -jar jenkins_client.jar -server http://HOST:PORT -username USERNAME -password PASSWORD -removeJobs .*Tests.*</li>
      </ul>
  </ol>
</p>
<p>
To execute action without any prompt, add <b>-f</b> at the end of the command line.
</p>
