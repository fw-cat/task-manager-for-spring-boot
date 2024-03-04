package jp.ac.ohara.taskManager.model;

import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jp.ac.ohara.taskManager.config.messages.ErrorMessages;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
@SQLDelete(sql = "UPDATE tasks SET deleted_at = NOW() WHERE id=?")
@SQLRestriction(value = "deleted_at IS NULL")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	private String title;

	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	private String description;

	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	@Email(message = ErrorMessages.EMAIL)
	private String priority;

	@PositiveOrZero(message = ErrorMessages.POSITIVE_OR_ZERO)
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDt;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updatedAt;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdAt;

}
