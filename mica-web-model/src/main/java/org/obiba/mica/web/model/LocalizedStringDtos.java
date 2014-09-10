package org.obiba.mica.web.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.obiba.mica.domain.LocalizedString;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component
class LocalizedStringDtos {

  Iterable<Mica.LocalizedStringDto> asDto(@SuppressWarnings("TypeMayBeWeakened") LocalizedString localizedString) {
    if (localizedString == null) return Collections.emptyList();
    return localizedString.entrySet().stream().map(
        entry -> Mica.LocalizedStringDto.newBuilder().setLang(entry.getKey()).setValue(entry.getValue())
            .build()
    ).collect(Collectors.toList());
  }

  LocalizedString fromDto(@Nullable Collection<Mica.LocalizedStringDto> dtos) {
    if(dtos == null || dtos.isEmpty()) return null;
    LocalizedString localizedString = new LocalizedString();
    for(Mica.LocalizedStringDto dto : dtos) {
      if(!Strings.isNullOrEmpty(dto.getValue())) {
        localizedString.put(dto.getLang(), dto.getValue());
      }
    }
    return localizedString;
  }

  @NotNull
  List<Mica.LocalizedStringDtos> asDtoList(@NotNull Collection<LocalizedString> localizedStrings) {
    return localizedStrings.stream().map(
        localizedString -> Mica.LocalizedStringDtos.newBuilder().addAllLocalizedStrings(asDto(localizedString)).build())
        .collect(Collectors.toList());
  }

  @NotNull
  List<LocalizedString> fromDtoList(@NotNull Collection<Mica.LocalizedStringDtos> dtos) {
    return dtos.stream().map(dto -> fromDto(dto.getLocalizedStringsList())).collect(Collectors.toList());
  }
}
