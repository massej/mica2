package org.obiba.mica.web.model;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.obiba.mica.file.Attachment;
import org.springframework.stereotype.Component;

import static org.obiba.mica.web.model.Mica.AttachmentDto;

@Component
class AttachmentDtos {

  @Inject
  private LocalizedStringDtos localizedStringDtos;

  @NotNull
  AttachmentDto asDto(@NotNull Attachment attachment) {
    AttachmentDto.Builder builder = AttachmentDto.newBuilder().setId(attachment.getId())
        .setFileName(attachment.getName()).setSize(attachment.getSize());
    if(attachment.getType() != null) builder.setType(attachment.getType());
    if(attachment.getDescription() != null) {
      builder.addAllDescription(localizedStringDtos.asDto(attachment.getDescription()));
    }
    if(attachment.getLang() != null) builder.setLang(attachment.getLang().toString());
    if(attachment.getMd5() != null) builder.setMd5(attachment.getMd5());
    return builder.build();
  }

  @NotNull
  Attachment fromDto(@NotNull Mica.AttachmentDtoOrBuilder dto) {
    Attachment attachment = new Attachment();
    attachment.setId(dto.getId());
    attachment.setName(dto.getFileName());
    if(dto.hasType()) attachment.setType(dto.getType());
    if(dto.getDescriptionCount() > 0) attachment.setDescription(localizedStringDtos.fromDto(dto.getDescriptionList()));
    if(dto.hasLang()) attachment.setLang(new Locale(dto.getLang()));
    attachment.setSize(dto.getSize());
    if(dto.hasMd5()) attachment.setMd5(dto.getMd5());
    attachment.setJustUploaded(dto.getJustUploaded());
    return attachment;
  }
}
