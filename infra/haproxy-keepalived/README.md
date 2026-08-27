# HAProxy + Keepalived high availability

Two Ubuntu 24.04 Lima VMs provide an active/passive HAProxy pair:

- `ha-node-1`: `192.168.104.3`, priority `150` (MASTER)
- `ha-node-2`: `192.168.104.1`, priority `100` (BACKUP)
- Virtual IP: `192.168.104.100`
- HAProxy frontend: port `80`
- Nginx test backends: port `8080`

Keepalived uses unicast VRRP over `eth0`. HAProxy health is checked every two
seconds. If HAProxy stops on the active node, its priority drops below the
backup node and the virtual IP moves to `ha-node-2`.

## Check

```bash
limactl list
limactl shell ha-node-1 -- curl -s http://192.168.104.100
limactl shell ha-node-1 -- ip address show eth0
limactl shell ha-node-2 -- ip address show eth0
```

## Failover test

```bash
limactl shell ha-node-1 -- sudo systemctl stop haproxy
limactl shell ha-node-2 -- ip address show eth0
limactl shell ha-node-2 -- curl -s http://192.168.104.100
limactl shell ha-node-1 -- sudo systemctl start haproxy
```
