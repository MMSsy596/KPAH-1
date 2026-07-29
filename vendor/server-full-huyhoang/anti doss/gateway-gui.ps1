# Gateway GUI for Windows Server 2022
# Run this script as Administrator
# It creates a TCP port proxy (gateway) and opens firewall for the listen port.

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

function Test-Admin {
    $principal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
    return $principal.IsInRole([Security.Principal.WindowsBuiltinRole]::Administrator)
}

function Add-Log([string]$msg) {
    $logBox.AppendText((Get-Date).ToString('HH:mm:ss') + ' - ' + $msg + [Environment]::NewLine)
}

$form = New-Object System.Windows.Forms.Form
$form.Text = "Gateway GUI"
$form.Size = New-Object System.Drawing.Size(420, 320)
$form.StartPosition = "CenterScreen"
$form.TopMost = $true

$lblListenPort = New-Object System.Windows.Forms.Label
$lblListenPort.Text = "Listen Port"
$lblListenPort.Location = New-Object System.Drawing.Point(20, 20)
$lblListenPort.AutoSize = $true

$txtListenPort = New-Object System.Windows.Forms.TextBox
$txtListenPort.Location = New-Object System.Drawing.Point(140, 18)
$txtListenPort.Size = New-Object System.Drawing.Size(200, 20)
$txtListenPort.Text = "19129"

$lblTargetIp = New-Object System.Windows.Forms.Label
$lblTargetIp.Text = "Target IP"
$lblTargetIp.Location = New-Object System.Drawing.Point(20, 55)
$lblTargetIp.AutoSize = $true

$txtTargetIp = New-Object System.Windows.Forms.TextBox
$txtTargetIp.Location = New-Object System.Drawing.Point(140, 53)
$txtTargetIp.Size = New-Object System.Drawing.Size(200, 20)
$txtTargetIp.Text = "127.0.0.1"

$lblTargetPort = New-Object System.Windows.Forms.Label
$lblTargetPort.Text = "Target Port"
$lblTargetPort.Location = New-Object System.Drawing.Point(20, 90)
$lblTargetPort.AutoSize = $true

$txtTargetPort = New-Object System.Windows.Forms.TextBox
$txtTargetPort.Location = New-Object System.Drawing.Point(140, 88)
$txtTargetPort.Size = New-Object System.Drawing.Size(200, 20)
$txtTargetPort.Text = "19129"

$btnStart = New-Object System.Windows.Forms.Button
$btnStart.Text = "Start Gateway"
$btnStart.Location = New-Object System.Drawing.Point(20, 125)
$btnStart.Size = New-Object System.Drawing.Size(150, 28)

$btnStop = New-Object System.Windows.Forms.Button
$btnStop.Text = "Stop Gateway"
$btnStop.Location = New-Object System.Drawing.Point(190, 125)
$btnStop.Size = New-Object System.Drawing.Size(150, 28)

$logBox = New-Object System.Windows.Forms.TextBox
$logBox.Location = New-Object System.Drawing.Point(20, 165)
$logBox.Size = New-Object System.Drawing.Size(360, 95)
$logBox.Multiline = $true
$logBox.ScrollBars = "Vertical"
$logBox.ReadOnly = $true

$btnStart.Add_Click({
    if (-not (Test-Admin)) {
        [System.Windows.Forms.MessageBox]::Show("Please run as Administrator.", "Admin Required") | Out-Null
        return
    }

    $listenPort = $txtListenPort.Text.Trim()
    $targetIp = $txtTargetIp.Text.Trim()
    $targetPort = $txtTargetPort.Text.Trim()

    if ($listenPort -notmatch '^[0-9]+$' -or $targetPort -notmatch '^[0-9]+$') {
        Add-Log "Invalid port."
        return
    }

    $ruleName = "KPAH Gateway $listenPort"

    Add-Log "Adding portproxy $listenPort -> $targetIp:$targetPort"
    cmd /c "netsh interface portproxy delete v4tov4 listenport=$listenPort listenaddress=0.0.0.0" | Out-Null
    cmd /c "netsh interface portproxy add v4tov4 listenport=$listenPort listenaddress=0.0.0.0 connectport=$targetPort connectaddress=$targetIp" | Out-Null

    Add-Log "Opening firewall for $listenPort"
    cmd /c "netsh advfirewall firewall delete rule name=\"$ruleName\"" | Out-Null
    cmd /c "netsh advfirewall firewall add rule name=\"$ruleName\" dir=in action=allow protocol=TCP localport=$listenPort" | Out-Null

    Add-Log "Gateway started. Client -> Gateway:$listenPort -> $targetIp:$targetPort"
})

$btnStop.Add_Click({
    if (-not (Test-Admin)) {
        [System.Windows.Forms.MessageBox]::Show("Please run as Administrator.", "Admin Required") | Out-Null
        return
    }

    $listenPort = $txtListenPort.Text.Trim()
    if ($listenPort -notmatch '^[0-9]+$') {
        Add-Log "Invalid port."
        return
    }
    $ruleName = "KPAH Gateway $listenPort"

    Add-Log "Removing portproxy for $listenPort"
    cmd /c "netsh interface portproxy delete v4tov4 listenport=$listenPort listenaddress=0.0.0.0" | Out-Null
    Add-Log "Removing firewall rule $ruleName"
    cmd /c "netsh advfirewall firewall delete rule name=\"$ruleName\"" | Out-Null

    Add-Log "Gateway stopped."
})

$form.Controls.AddRange(@(
    $lblListenPort, $txtListenPort,
    $lblTargetIp, $txtTargetIp,
    $lblTargetPort, $txtTargetPort,
    $btnStart, $btnStop,
    $logBox
))

$form.Add_Shown({ $form.Activate() })
[void]$form.ShowDialog()
