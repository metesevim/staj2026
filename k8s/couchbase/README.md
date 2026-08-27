# Couchbase ve Kubernetes

## Couchbase nedir?

Couchbase Server; veriyi anahtar/değer çifti olarak tutan, değer tarafında JSON document kullanabilen dağıtık bir NoSQL document veritabanıdır. Bellek-öncelikli veri erişimi ve yönetilen cache katmanı düşük gecikmeli işlemleri destekler. SQL benzeri SQL++ dili JSON document'ları sorgulamak için kullanılır.

Veri hiyerarşisi şöyledir:

```text
Bucket -> Scope -> Collection -> Document
```

- **Bucket:** En üst veri ve kaynak sınırıdır.
- **Scope:** İlgili collection'ları mantıksal olarak gruplar.
- **Collection:** Document'ların tutulduğu en küçük kapsayıcıdır.
- **Document:** Benzersiz bir key ve JSON value'dan oluşur.

Yeni bir bucket otomatik olarak `_default` scope ve `_default` collection ile gelir. Couchbase node'ları cluster hâlinde çalışabilir; veri vBucket'lar üzerinden dağıtılabilir, yatay ölçekleme yapılabilir ve replica kullanılarak node arızalarına karşı yüksek erişilebilirlik sağlanabilir.

PostgreSQL ilişkisel tablo, satır, şema ve join modelini temel alır. Couchbase ise esnek JSON document modelini ve key tabanlı erişimi temel alır. SQL++ ilişkisel SQL'e benzer bir sorgu deneyimi sunsa da veri modeli ve dağıtım yaklaşımı farklıdır.

## Bu yerel Kubernetes kurulumu

Bu manifest eğitim ve yerel geliştirme için tek node Couchbase Community Edition çalıştırır:

- Web yönetimi: `8091`
- Query Service / SQL++: `8093`
- Data Service: `11210`
- Dış erişim: `NodePort` ve macOS Docker driver üzerinde `minikube service couchbase --url`
- Cluster içi sabit node kimliği: `couchbase-headless.default.svc.cluster.local`

Admin parolası manifestte tutulmaz. Canlı doğrulamalarda repo dışında oluşturulan `couchbase-admin` Kubernetes Secret'ı kullanılır.

Deployment kalıcı volume kullanmadığı için pod yeniden oluşturulursa cluster ayarları ve tüm veriler kaybolur. Bu seçim yalnızca issue kapsamındaki yerel kurulum içindir. Production ortamında kalıcı volume, çoklu node, replica, backup, güvenlik ve yaşam döngüsü yönetimi gerekir; bunlar için Couchbase Autonomous Operator değerlendirilmelidir.

## Kaynaklar

- [Couchbase Server genel bakış](https://docs.couchbase.com/server/current/introduction/intro.html)
- [Bucket, scope ve collection](https://docs.couchbase.com/server/current/learn/data/scopes-and-collections.html)
- [SQL++ ile ilk sorgu](https://docs.couchbase.com/server/current/getting-started/try-a-query.html)
- [Couchbase Kubernetes Operator](https://docs.couchbase.com/operator/current/overview.html)
