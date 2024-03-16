package jp.ac.ohara.taskManager.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jp.ac.ohara.taskManager.config.messages.ErrorMessages;
import jp.ac.ohara.taskManager.config.utility.DateTimeUtility;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

	@PositiveOrZero(message = ErrorMessages.POSITIVE_OR_ZERO)
	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Future(message = ErrorMessages.FUTURE_MESSAGE)
	private Date dueDt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updatedAt;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdAt;

	private Date deletedAt;

	// タグ一覧（関節テーブルがあるのでただの変数とする）
	// private List<Tag> tags = new ArrayList<Tag>();

	/**
	 * 期日のLocalDateを取得
	 * @return
	 */
	public LocalDate getDueLocalDt() {
		return this.dueDt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * 期日の文字列を取得
	 * @return
	 */
	public String getDueDay() {
		// 現在日を取得
		LocalDate nowDt = LocalDate.now();
		Period period = Period.between(nowDt, this.getDueLocalDt());

		return String.format("%s（あと%d日）", DateTimeUtility.DAY_FORMAT.format(this.dueDt), period.getDays());
	}
}
