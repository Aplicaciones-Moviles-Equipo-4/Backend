-- Adds organizer ownership to social events so they can be scoped to the organizer (profileId)
-- that created them. Nullable to preserve pre-existing rows created before this column existed.
ALTER TABLE social_events ADD COLUMN organizer_id BIGINT NULL;
