
for d in ./*/ ; do (cd "$d" && redis-server ./redis.conf &); done

redis-cli --cluster create 127.0.0.1:30001 127.0.0.1:30002 \
127.0.0.1:30003 127.0.0.1:30004 127.0.0.1:30005 127.0.0.1:30006 \
--cluster-replicas 1 &
