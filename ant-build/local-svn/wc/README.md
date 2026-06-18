# Lab 3: Apache Ant Build Script

## Суть задания

Нужно было написать сценарий для `Apache Ant`, который автоматизирует:

- компиляцию проекта;
- сборку и упаковку в исполняемый `jar`;
- запуск `JUnit`-тестов;
- генерацию документации;
- преобразование локализаций через `native2ascii`;
- дополнительные цели `music`, `history`, `team`, `alt`.

Также все параметры сценария должны быть вынесены в отдельный файл, а `MANIFEST.MF` должен содержать версию и запускаемый класс.

---

## Что сделано

- Создан Ant-сценарий `build.xml` с целями:
  - `compile`, `build`, `clean`, `test`, `doc`,
  - `native2ascii`, `music`, `history`, `team`, `alt`.
- Все параметры вынесены в `build.properties`.
- В `MANIFEST.MF` добавлены:
  - версия приложения (`Implementation-Version`),
  - запускаемый класс (`Main-Class`),
  - дата сборки (`Built-Date`),
  - контрольные суммы `Jar-MD5` и `Jar-SHA1`.
- Добавлены простые `JUnit`-тесты в `src/test/java`.
- Для `native2ascii` добавлены файлы локализации:
  - `src/main/resources/i18n/messages.properties`
  - `src/main/resources/i18n/messages_ru.properties`
- Строки сообщений из валидаторов вынесены в локализацию через `org.example.i18n.Messages`.
- Для `team` реализована сборка 3 предыдущих ревизий через вызов цели `build`.
- Для `alt` реализована альтернативная сборка через `build` с `replace` и `replaceregexp`.

---

## Быстрый запуск

Запускать команды из корня проекта:

```bash
./tools/apache-ant/apache-ant-1.10.17/bin/ant <target>
```

Примеры:

```bash
# очистка
./tools/apache-ant/apache-ant-1.10.17/bin/ant clean

# компиляция
./tools/apache-ant/apache-ant-1.10.17/bin/ant compile

# сборка jar
./tools/apache-ant/apache-ant-1.10.17/bin/ant build

# тесты (build выполняется автоматически по depends)
./tools/apache-ant/apache-ant-1.10.17/bin/ant test

# документация + checksums + обновление jar
./tools/apache-ant/apache-ant-1.10.17/bin/ant doc

# native2ascii
./tools/apache-ant/apache-ant-1.10.17/bin/ant native2ascii

# team: сборка 3 предыдущих ревизий и zip
./tools/apache-ant/apache-ant-1.10.17/bin/ant team

# alt: альтернативная версия с переименованиями
./tools/apache-ant/apache-ant-1.10.17/bin/ant alt
```

---

## Как тестировать

Рекомендуемый сценарий полной проверки:

```bash
./tools/apache-ant/apache-ant-1.10.17/bin/ant clean test doc native2ascii team alt
```

Успех: в конце должно быть `BUILD SUCCESSFUL`.

---

## Как смотреть результат

Основные артефакты в `ant-build/`:

- основной jar: `ant-build/dist/lab3.jar`
- альтернативный jar: `ant-build/dist/lab3-alt.jar`
- zip по ревизиям: `ant-build/team-builds.zip`
- javadoc: `ant-build/docs/javadoc/`
- checksum-файлы: `ant-build/checksums/`
- native2ascii output: `ant-build/native2ascii/output/`

Полезные команды проверки:

```bash
# содержимое MANIFEST в jar
unzip -p ant-build/dist/lab3.jar META-INF/MANIFEST.MF

# содержимое zip для team
unzip -l ant-build/team-builds.zip

# список файлов после native2ascii
ls ant-build/native2ascii/output
```

---

## Что проверяется по требованиям

- `compile` — компиляция проекта.
- `build` — компиляция + упаковка в jar, компиляция вызывается через `compile`.
- `clean` — удаление собранных и временных файлов.
- `test` — запуск `JUnit` после `build`.
- `doc` — javadoc + MD5/SHA-1 + добавление в jar.
- `native2ascii` — конвертация копий локализаций.
- `music` — звуковое уведомление после `build`.
- `history` — поиск рабочей SVN-ревизии при проблемной сборке.
- `team` — сборка 3 предыдущих ревизий через `build` и упаковка в zip.
- `alt` — альтернативная версия через `replace/replaceregexp` и сборка через `build`.
