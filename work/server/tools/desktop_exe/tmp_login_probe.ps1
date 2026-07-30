$ErrorActionPreference = "Stop"

$serverHost = "163.61.183.129"
$port = 19129
$user = "hoang1"
$pass = "1"
$version = "2.7.0"
$platform = "nokia/1/1"
$bigProvider = "0"
$provider = "0"
$agent = "0"
$firmware = 0
$width = 240
$vsTile = 0
$serverId = 0

function Write-Utf8Short([System.IO.Stream]$stream, [string]$text) {
    $bytes = [System.Text.Encoding]::UTF8.GetBytes($text)
    $len = [uint16]$bytes.Length
    $stream.WriteByte([byte](($len -shr 8) -band 0xFF))
    $stream.WriteByte([byte]($len -band 0xFF))
    $stream.Write($bytes, 0, $bytes.Length)
}

function Xor-Byte([byte]$value, [byte[]]$key, [ref]$idx) {
    $out = [byte]($value -bxor $key[$idx.Value])
    $idx.Value = ($idx.Value + 1) % $key.Length
    return $out
}

function To-SignedByte([int]$value) {
    if ($value -gt 127) {
        return ($value - 256)
    }
    return $value
}

$client = [System.Net.Sockets.TcpClient]::new()
$client.ReceiveTimeout = 5000
$client.SendTimeout = 5000
$client.Connect($serverHost, $port)
$stream = $client.GetStream()

$stream.WriteByte(0xD8)
$stream.WriteByte(0)
$stream.WriteByte(0)
$stream.Flush()

$cmdByte = $stream.ReadByte()
if ($cmdByte -lt 0) {
    throw "EOF before handshake cmd"
}
$lenHi = $stream.ReadByte()
$lenLo = $stream.ReadByte()
if ($lenHi -lt 0 -or $lenLo -lt 0) {
    throw "EOF before handshake length"
}

$cmd = To-SignedByte $cmdByte
$size = ($lenHi -shl 8) -bor $lenLo
$data = New-Object byte[] $size
$offset = 0
while ($offset -lt $size) {
    $read = $stream.Read($data, $offset, $size - $offset)
    if ($read -le 0) {
        throw "EOF while reading handshake payload"
    }
    $offset += $read
}

Write-Output "recv1 cmd=$cmd size=$size data=$([BitConverter]::ToString($data))"

if ($cmd -ne -40) {
    throw "Expected handshake cmd -40, got $cmd"
}

$keyLen = [int](To-SignedByte $data[0])
$key = New-Object byte[] $keyLen
[Array]::Copy($data, 1, $key, 0, $keyLen)
Write-Output "key=$([BitConverter]::ToString($key))"

$payloadStream = New-Object System.IO.MemoryStream
Write-Utf8Short $payloadStream $user
Write-Utf8Short $payloadStream $pass
Write-Utf8Short $payloadStream $version
Write-Utf8Short $payloadStream $platform
Write-Utf8Short $payloadStream $bigProvider
Write-Utf8Short $payloadStream $provider
Write-Utf8Short $payloadStream $agent
$payloadStream.WriteByte($firmware)
$payloadStream.WriteByte([byte](($width -shr 8) -band 0xFF))
$payloadStream.WriteByte([byte]($width -band 0xFF))
$payloadStream.WriteByte($vsTile)
$payloadStream.WriteByte($serverId)
$payload = $payloadStream.ToArray()

Write-Output "send login payload size=$($payload.Length) head=$([BitConverter]::ToString($payload[0..([Math]::Min($payload.Length - 1, 31))]))"

$writeIndex = [ref]0
$encCmd = Xor-Byte ([byte]1) $key $writeIndex
$encLenHi = Xor-Byte ([byte](($payload.Length -shr 8) -band 0xFF)) $key $writeIndex
$encLenLo = Xor-Byte ([byte]($payload.Length -band 0xFF)) $key $writeIndex
$encPayload = New-Object byte[] ($payload.Length)
for ($i = 0; $i -lt $payload.Length; $i++) {
    $encPayload[$i] = Xor-Byte $payload[$i] $key $writeIndex
}

$stream.WriteByte($encCmd)
$stream.WriteByte($encLenHi)
$stream.WriteByte($encLenLo)
$stream.Write($encPayload, 0, $encPayload.Length)
$stream.Flush()

$readIndex = [ref]0
for ($n = 0; $n -lt 20; $n++) {
    try {
        $rawCmd = $stream.ReadByte()
        if ($rawCmd -lt 0) {
            Write-Output "recv[$n]=EOF"
            break
        }
        $cmd2 = To-SignedByte (Xor-Byte ([byte]$rawCmd) $key $readIndex)
        $rawHi = $stream.ReadByte()
        $rawLo = $stream.ReadByte()
        if ($rawHi -lt 0 -or $rawLo -lt 0) {
            Write-Output "recv[$n]=EOF-length"
            break
        }
        $size2 = ((Xor-Byte ([byte]$rawHi) $key $readIndex) -shl 8) -bor (Xor-Byte ([byte]$rawLo) $key $readIndex)
        $data2 = New-Object byte[] $size2
        $offset = 0
        while ($offset -lt $size2) {
            $read = $stream.Read($data2, $offset, $size2 - $offset)
            if ($read -le 0) {
                throw "EOF inside payload"
            }
            $offset += $read
        }
        for ($i = 0; $i -lt $data2.Length; $i++) {
            $data2[$i] = Xor-Byte $data2[$i] $key $readIndex
        }
        $headCount = if ($data2.Length -gt 0) { [Math]::Min($data2.Length - 1, 31) } else { -1 }
        $head = if ($headCount -ge 0) { [BitConverter]::ToString($data2[0..$headCount]) } else { "" }
        Write-Output "recv2[$n] cmd=$cmd2 size=$size2 head=$head"
    } catch {
        Write-Output "recv2[$n] exception=$($_.Exception.Message)"
        break
    }
}

$client.Close()
